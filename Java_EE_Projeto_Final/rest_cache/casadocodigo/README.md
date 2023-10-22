# Java EE parte 2: Sua loja online com HTML, REST e Cache


### Links
- [Melhorando a UX da Administração](#anc1)
- [Carrinho de Compras com HTML e JSF](#anc2)
- [Salvando a Compra com JSON](#anc3)
- [Usando serviços REST e requisições Assíncronas](#anc4)
- [Conhecendo e Utilizando Cache no JavaEE](#anc5)

- [Site](http://localhost:8080/casadocodigo/index.xhtml)
##

<a name="anc1"></a>

## Melhorando a UX da Administração 
- [Bootstrap](https://getbootstrap.com/docs/3.4/getting-started/)
- Descompacte e dentro da pasta descompactada você deve estar vendo css, fonts e js. Copei o conteúdo da pasta css baixada para dentro da nossa pasta src/main/webapp/resources/css. Faça o mesmo procedimento para a pasta js que também já temos em nosso projeto, dentro de src/main/webapp/resources/js. * Já a pasta fonts que não temos ainda no projeto, copie a pasta inteira para src/main/webapp/resources.
- Adicionando o boostrap via jsf
```
<h:head>
    <h:outputStylesheet library="css" name="bootstrap.min.css" />
</h:head>

```

<a name="anc2"></a>

## Carrinho de Compras com HTML e JSF
- Nesse botão de comprar, vamos chamar nosso Bean para o método add. Porém usando o HTML como sendo reconhecido pelo JSF, adicionando o jsf:action conforme abaixo.

- É importante que seja passado para o método o id do Livro, para que dessa forma seja possível recuperar a informação do livro que se deseja adicionar no carrinho:

```
jsf:action="#{carrinhoComprasBean.add(livroDetalheBean.id)}"
```

> Qual o propósito final do Passthrough?
- Permitir que componentes JSF possam usar atributos do HTML.

<a name="anc3"></a>

## Salvando a Compra com JSON

<a name="anc4"></a>

## Usando serviços REST e requisições Assíncronas


<a name="anc5"></a>

## Conhecendo e Utilizando Cache no JavaEE