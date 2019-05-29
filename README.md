# Star Wars Planets API

Star wars planets API é um aplicação que recebe dados de planetas de star wars do usuário e os armazena no banco de dados.

## Endpoints

### - Adcicionar Planeta

**Request:**

> POST /planetas

```javascript
{
  nome: 'Tatooine',
  clima: 'arid',
  terreno: 'desert'
}
```

**Response:**

> 200 OK

```javascript
{
  id: '5cec899a4156374e0bb8d6a9',
  nome: 'Tatooine',
  clima: 'arid',
  terreno: 'desert',
  aparicoesEmFilmes: 5
}
```

### - Listar os planetas

**Request:**

> GET /planetas

**Response:**

> 200 OK

```javascript
[
  {
    id: '5cec899a4156374e0bb8d6a9',
    nome: 'Alderaan',
    clima: 'temperate',
    terreno: 'grasslands, mountains',
    aparicoesEmFilmes: 2
  },
  {
    id: '5cee00344156370766b59be6',
    nome: 'Dagobah',
    clima: 'murky',
    terreno: 'swamp, jungles',
    aparicoesEmFilmes: 3
  }
];
```

### - Buscar por nome

**Request:**

> GET /planetas/busca?nome=Tatooine

**Response:**

> 200 OK

```javascript
{
  id: '5cec899a4156374e0bb8d6a9',
  nome: 'Tatooine',
  clima: 'arid',
  terreno: 'desert',
  aparicoesEmFilmes: 5
}
```

### - Buscar por Id

**Request:**

> GET /planetas/{id}

**Response:**

> 200 OK

```javascript
{
  id: '5cec899a4156374e0bb8d6a9',
  nome: 'Tatooine',
  clima: 'arid',
  terreno: 'desert',
  aparicoesEmFilmes: 5
}
```

### - Remover planeta

**Request:**

> DELETE /planetas/{id}

**Response:**

> 200 OK

## Banco de dado

A aplicação utiliza **MongoDB** como banco de dados conectando-se a um banco chamado **starwarsplanets** com o host e a porta padrão, essas propriedades podem ser alteradas no arquivo **application.properties** em /src/main/resources.

```properties
spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
spring.data.mongodb.database=starwarsplanets
```
