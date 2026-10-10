#!/usr/bin/env bash
# Teste rápido dos endpoints principais (roda no GitHub Actions)
set -e
API=http://localhost:8080/backend

check() { # check <descrição> <status esperado> <status recebido>
  if [ "$2" != "$3" ]; then echo "FALHOU: $1 (esperado $2, veio $3)"; cat /tmp/body; echo; exit 1; fi
  echo "ok  - $1 ($3)"
}
req() { curl -s -o /tmp/body -w "%{http_code}" "$@"; }

check "listar planos" 200 "$(req $API/planos)"
PLANO=$(jq -r '.[] | select(.nome=="Trimestral") | .id' /tmp/body)

check "cadastrar aluno" 201 "$(req -X POST $API/alunos -H 'Content-Type: application/json' \
  -d '{"nome":"Felipe Bitello","cpf":"12345678900","email":"felipe@email.com","dataNascimento":"2005-03-15"}')"
ALUNO=$(jq -r .id /tmp/body)
cat /tmp/body; echo

check "cpf duplicado" 400 "$(req -X POST $API/alunos -H 'Content-Type: application/json' \
  -d '{"nome":"Outro","cpf":"12345678900"}')"
check "cpf invalido" 400 "$(req -X POST $API/alunos -H 'Content-Type: application/json' -d '{"nome":"X","cpf":"123"}')"
check "buscar aluno" 200 "$(req $API/alunos/$ALUNO)"
check "filtrar por nome" 200 "$(req "$API/alunos?nome=feli")"
grep -q Felipe /tmp/body
check "aluno inexistente" 404 "$(req $API/alunos/00000000-0000-0000-0000-000000000000)"
check "atualizar aluno" 200 "$(req -X PUT $API/alunos/$ALUNO -H 'Content-Type: application/json' \
  -d '{"nome":"Felipe B. Pedro","cpf":"12345678900","telefone":"51999998888"}')"

check "check-in sem matricula" 400 "$(req -X POST $API/alunos/$ALUNO/check-ins)"

check "matricular (PIX)" 201 "$(req -X POST $API/matriculas -H 'Content-Type: application/json' \
  -d "{\"alunoId\":\"$ALUNO\",\"planoId\":\"$PLANO\",\"formaPagamento\":\"PIX\"}")"
cat /tmp/body; echo
[ "$(jq -r .valorTotal /tmp/body)" = "269.73" ] || { echo "FALHOU: valor esperado 269.73 (99.90 x 3 - 10%)"; exit 1; }
MATRICULA=$(jq -r .id /tmp/body)

check "segunda matricula bloqueada" 400 "$(req -X POST $API/matriculas -H 'Content-Type: application/json' \
  -d "{\"alunoId\":\"$ALUNO\",\"planoId\":\"$PLANO\",\"formaPagamento\":\"CARTAO_CREDITO\"}")"
check "check-in" 201 "$(req -X POST $API/alunos/$ALUNO/check-ins)"
check "listar check-ins" 200 "$(req $API/alunos/$ALUNO/check-ins)"
check "trancar" 200 "$(req -X PATCH "$API/matriculas/$MATRICULA?action=trancar")"
grep -q TRANCADA /tmp/body
check "trancar de novo" 400 "$(req -X PATCH "$API/matriculas/$MATRICULA?action=trancar")"
check "check-in com matricula trancada" 400 "$(req -X POST $API/alunos/$ALUNO/check-ins)"
check "reativar" 200 "$(req -X PATCH "$API/matriculas/$MATRICULA?action=reativar")"
check "cancelar" 200 "$(req -X PATCH "$API/matriculas/$MATRICULA?action=cancelar")"
check "reativar cancelada" 400 "$(req -X PATCH "$API/matriculas/$MATRICULA?action=reativar")"
check "acao invalida" 400 "$(req -X PATCH "$API/matriculas/$MATRICULA?action=voar")"
check "filtrar matriculas por status" 200 "$(req "$API/matriculas?status=CANCELADA")"
check "historico do aluno" 200 "$(req $API/alunos/$ALUNO/matriculas)"
check "excluir aluno com matricula" 409 "$(req -X DELETE $API/alunos/$ALUNO)"

check "criar plano" 201 "$(req -X POST $API/planos -H 'Content-Type: application/json' \
  -d '{"nome":"Semestral","valorMensal":89.90,"duracaoMeses":6}')"
NOVO=$(jq -r .id /tmp/body)
check "plano invalido" 400 "$(req -X POST $API/planos -H 'Content-Type: application/json' -d '{"nome":"X","valorMensal":0,"duracaoMeses":1}')"
check "excluir plano" 204 "$(req -X DELETE $API/planos/$NOVO)"
check "swagger ui" 200 "$(curl -s -L -o /tmp/body -w '%{http_code}' $API/swagger-ui.html)"

echo "Todos os testes passaram"
