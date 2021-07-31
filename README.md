Requerimentos: `Docker || JAVA 8 && GRADLE 6.X.X`

Depois de clonar o projeto, na raiz do **Dockerfile** fazer o build da imagem com o seguinte comando:

    $ docker image build -t marvel-api .

Assim que a imagem for criada a partir do descritor, suba o container com o comando:
    
    $ docker container run -p 8080:8080 marvel-api

Então o servidor vai subir e criar as tabelas necessárias, mas para criar os dados básicos para teste é necessário acessar o endpoint `/characters/${characterID}` (post) e enviar um json que será o personagem.

Exemplo:

```json
{
  "name": "mysql",
  "description": "My fav database",
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
    {URL} = localhost:8080 || https://knautiluz-characters.herokuapp.com
    [GET] [POST] {URL}/characters/
    [GET] [PUT] [DELETE] {URL}/characters/{characterID}

Após essa configuração os endpoints implementados `/characters` e `/characters/{characterId}` já estarão disponiveis.

Para o [heartbeat](https://pt.wikiqube.net/wiki/Heartbeat_(computing)) acesse o index path = `/`

No endpoint `/characters` os seguintes parametros de busca foram implementados:

```
1. name { ex: '?name=Bakugo' }
2. nameStartsWith { ex: '?nameStartsWith=Bak' }
3. modifiedSince { ex: '?modifiedSince=30/08/1992' }
4. orderBy { name || modified; ex: '?orderBy=name' for ASC '?orderBy=-name' for DESC }
7. limit { result limit;  ex: '?limit=10' }
8. offset { skip values; ex: '?offset=1' }
