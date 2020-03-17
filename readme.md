Para compilar basta executar o **gradle clean build** *(linux)* ou **gradlew clean build** *(windows)*

depois de compilar o projeto é necessário acessar build/libs/ e executar java -jar *nome do jar*

Então o servidor vai subir e criar as tabelas necessárias, mas para criar os dados básicos para teste é necessário acessar o endpoint **/start** que vai gerar dois personagens suficientes para testar os filtros, caso seja necessário gerar mais personagens, basta acessar o endpoint **/new** *(post)* e enviar um json que será o personagem. Exemplo:

```json
{
  "name": "mysql",
  "description": "My fav database",
  "modified": 1001564564,
  "resourceURI": "https://mysql.knautiluz.net",
  "urls": [{"type": "wiki", "url": "https://wiki.knautiluz.net"}],
  "thumbnail": {
    "path": "https://knautiluz.net/mysql",
    "extension": "png"
  },
  "comics": [
    {
      "collectionURI": "knautiluz.net/collections",
      "items": [
        {
          "resourceURI": "knautiluz.net/resources",
          "name": "resources of knautiluz"
        }
      ]
    }
  ]
}
```

    localhost:8080/start
    localhost:8080/new
    localhost:8080/characters
    localhost:8080/characters/{characterID}

após essa configuração os endpoints implementados **/characters** e **/characters/{characterId}** já estarão disponiveis.

No endpoint **/characters** os seguintes parametros de busca foram implementados:

1. name
2. modifiedSince
3. nameStartsWith
4. orderBy (name, -name, modified, -modified)
5. limit
6. offset

Eu irei incluir o jar a ser executado na pasta **/dextra/jar/** para caso ocorra algum problema na execução do Gradle. Então é só acessar a pasta onde está o jar e executar java -jar *nome-do-jar*
