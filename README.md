Requerimentos: **Java-8**, **Gradle 6.2.2**, **Docker**

Depois de clonar o projeto, na raiz do **Dockerfile** fazer o build da imagem com o seguinte comando:

    $ docker image build -t marvel-api .

Assim que a imagem for criada a partir do descritor, suba o container com o comando:
    
    $ docker container run -p 8080:8080 marvel-api

Então o servidor vai subir e criar as tabelas necessárias, mas para criar os dados básicos para teste é necessário acessar o endpoint **/start** (post) que vai gerar dois personagens suficientes para testar os filtros, caso seja necessário gerar mais personagens, basta acessar o endpoint **/new** *(post)* e enviar um json que será o personagem. Exemplo:

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
2. nameStartsWith
3. modifiedSince
4. nameStartsWith
5. modifiedSince
6. orderBy
7. limit
8. offset
