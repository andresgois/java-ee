# JAVA EE

### Módulo 6 JSF 2
- [Apresentação e teste de avaliação Ver primeiro vídeo](#anc1)
- [Começando com Primefaces](#anc2)
- [Componentes ricos no formulário](#anc3)
- [Mais usabilidade na página de livros](#anc4)
- [Dados tabulares com Primefaces](#anc5)
- [Melhorando a navegação com menus](#anc6)
- [Aplicando temas](#anc7)
- [Gráfico com Primefaces](#anc8)

# Módulo 6
<a name="anc1"></a>

## Apresentação e teste de avaliação Ver primeiro vídeo

### Apresentação do projeto a criar
- Nesse curso vamos focar os componentes da biblioteca famosa Primefaces! Com ela, não só iremos melhorar a interface da nossa aplicação, como também melhoraremos a sua usabilidade.

- Vou apresentar o projeto que criaremos nesse cursos e mostrar as telas já com Primefaces. Na página de login vemos um medidor de segurança de senhas, um menu para navegação e realizar o logout, máscaras nos campos, um calendário para selecionar a data, uma tabela paginada, onde podemos filtrar e ordenar os dados facilmente, além de uma nova página de vendas e diversas outras melhorias!

- Para vocês terem uma ideia de como a nossa aplicação ficará após o uso do Primefaces, seguem algumas imagens:

- [tela_com_prime](./asserts/tela_com_prime.png)

- Na especificação JSF, como definir uma classe gerenciada pelo JSF?
    - *@ManagedBean public class LivroBean {*
- Quais são as características comuns no desenvolvimento RAD (Rapid Application Development) e que também podem ser encontradas no desenvolvimento com JavaServer Faces?
    - *Componentes ricos, orientado ao evento e mantém o estado dos componentes (stateful).*
- Os componentes definidos na página xhtml serão instanciados pelo controlador padrão do JSF. O resultado disso é a árvore de componentes que fica guardada em memória. Em que momento o controlador cria esta árvore?
    - *Apenas na primeira requisição (no primeiro HTTP GET).*
- Qual é o efeito de anotar um bean gerenciado pelo JSF com a anotação @ViewScoped?
    - *A instância desse bean existirá enquanto a tela existir.*
- Cada componente possui atributos especiais para criar uma ligação com uma classe/bean. No caso do h:commandButton, que atributo devemos declarar para indicar que bean usaremos?
    - *action*
- JSF usa fases bem definidas que são executadas em cada requisição. O que acontece na terceira fase que se chama de PROCESS_VALIDATION?
    - *Na terceira fase acontece a conversão, se for preciso, e a validação.*

- Veja o pedaço de código xhtml:
```
<h:commandButton value="Gravar" action="#{livroBean.gravar}"  />
```
- O método gravar do livroBean associado com o comando em cima é executado em qual fase?
    - *INVOKE_APPLICATION*


<a name="anc2"></a>

## Começando com Primefaces

### Declaração do namespace
- [PrimeFaces](https://www.primefaces.org/)

- Os primeiros componentes do Primefaces
    - A ideia agora é utilizar cada vez mais os componentes do Primefaces. Vamos "primefacear" todo o conteúdo do formulário, começando pelo título "Login", ele passará a ser um <p:outputPanel>. Depois, o fieldset e legend, agora teremos um <p:fieldset>, que já tem um atributo legend, então podemos substituí-los. Após isso, a ideia é substituir todos os componentes padrões do JSF, que utilizam o prefixo h, pelo prefixo p, ou seja, o Primefaces não só define componentes novos, como também redefine os componentes padrões do JSF. A razão dele fazer isso é que o Primefaces dá "algo a mais" para esses componentes, pode ser que o componente já faça ajax, ou que já venha com um CSS aplicado, etc. Ou seja, não precisamos reaprender a API.

- `xmlns:p="http://primefaces.org/ui"`

### Ajax por padrão
- O botão do Primefaces já faz ajax por padrão, que não é um comportamento do commandButton padrão. Então, já que ele faz ajax por padrão, precisamos definir o que queremos submeter e quais partes da tela queremos renderizar, o que não estamos fazendo no momento, já que atualmente, quando clicamos no botão para efetuar login, só estamos submetendo o botão e não os campos do formulário. Então vamos mudar esse comportamento, para isso o Primefaces tem dois atributos, o process e o update. No process dizemos quais componentes queremos submeter, no nosso caso queremos submeter todo o formulário, então utilizaremos @form; e no update dizemos o que queremos atualizar na página, e no nosso caso também é o formulário, logo também terá como valor @form. Então o commandButton ficará assim:

```
<p:commandButton value="Efetue Login" action="#{loginBean.efetuaLogin}" update="@form" process="@form" />
```

- Tem um script para popular as tabelas na raiz do projeto.

- Sobre o Primefaces podemos dizer que:
    - A) São componentes que estendem os componentes padrões do JSF adicionando novas funcionalidades, inclusive criando componentes inéditos.
    - B) Permite aplicar um atrativo estético em nossas páginas sem conhecimento técnico de CSS com pouco esforço através do seu sistema de temas.
    - C) Abstrai muitas vezes o uso de bibliotecas como jQuery ou até mesmo o uso de JavaScript para tarefas corriqueiras como o uso máscaras e de calendários.

<a name="anc3"></a>

## Componentes ricos no formulário

```
<p:commandLink styleClass="ui-icon ui-icon-pencil" style="float:left; margin-right: 10px;"
    update=":autor">
    <f:setPropertyActionListener value="#{autor}" target="#{autorBean.autor}" />
</p:commandLink>

<p:commandLink styleClass="ui-icon ui-icon-trash" style="float:left; margin-right: 10px;"
    action="#{autorBean.remover(autor)}" update="@form" />
```

## O Primefaces também oferece ícones para utilizarmos na nossa aplicação, você pode vê-los aqui. Vamos utilizá-los na nossa aplicação, no lugar dos ícones do jQuery.

- Primeiramente, a ideia é copiar do exemplo e colar na nossa página, para definir o ícone de alteração, utilizamos icon="fa fa-fw fa-edit" e o de remoção é icon="fa fa-fw fa-remove":

```
<h:form id="formTabelaAutors">
    <p:dataList value="#{autorBean.autores}" var="autor" type="definition">
        <f:facet name="header">
            Autores
        </f:facet>

        <p:commandButton icon="fa fa-fw fa-edit" update=":autor">
            <f:setPropertyActionListener value="#{autor}" target="#{autorBean.autor}" />
        </p:commandButton>

        <p:commandButton icon="fa fa-fw fa-remove" action="#{autorBean.remover(autor)}" 
            update="@form" />

        #{autor.nome} - #{autor.email}
    </p:dataList>
</h:form>
```

- Mas não funciona... Se consultarmos a documentação dos ícones, vemos que precisamos definir um context-param no web.xml. Então vamos fazer isso:

```
<context-param>
    <param-name>primefaces.FONT_AWESOME</param-name>
    <param-value>true</param-value>
</context-param>
```

- 1 - Aprendemos em JSF possui o componente `<f:passThroughtAttribute>` que permite usarmos componentes do HTML5 que não são suportados pelo framework. Contudo, com Primefaces, este recurso nem sempre funciona como esperado, porque a própria biblioteca já possui um outro recurso para conseguir a mesma finalidade. Altere seu componente que captura o e-mail do usuário e utilize f:attribute em vez de `<f:passThroughtAttribute>`.

- 2 - Use o componente `<f:validateRegex>` para validar o campo e-mail. Aliás, faça que validação seja disparada quando o usuário deixar o campo (através do AJAX).
- 3 - Lance mão do validatorMessage para exibir uma mensagem de validação do campo e-mail mais elegante.

<a name="anc4"></a>

## Mais usabilidade na página de livros
- atualizando tela de livros com primefaces
- Máscara para ISBN: 123-4-56-789101-1
```
<p:inputMask> E atributo mask="999-9-99-999999-9"
```
- Queremos que o Calendar também siga um padrão, e que seja mais fácil para o usuário inserir o dado.
```
<p:calendar id="data" pattern="dd/MM/yyyy" mask="true">
```

<a name="anc5"></a>

## Dados tabulares com Primefaces

### Ajustando dataTable e column
- Adicione o sortBy para ordenar pelo titulo do livro e teste o código
```
<p:column headerText="Título" sortBy="#{livro.titulo}">
        <h:outputText value="#{livro.titulo}" />
</p:column>
```
- Como podemos então, adicionar páginas para a tabela de livros?
```
<p:dataTable value="#{livroBean.livros}" var="livro" id="tabelaLivros" 
    paginator="true" rows="5">
        <!-- conteúdo da tabela -->
</p:dataTable>
```

- aplicaremos o Filter para o usuário poder buscar o título do livro que ele quiser. Para isso, temos que adicionar o atributo filterBy na coluna que queremos, que no caso, é a de Livros.

```
<p:column headerText="Título" sortBy="#{livro.titulo}" 
    filterBy="#{livro.titulo}" filterMatchMode="startsWith">
    <h:outputText value="#{livro.titulo}" />
</p:column>
```
- Existem outros MatchModes como `endsWidth`, `contains`, `exact`, `equals`, `lte` (menos ou igual) e `gte` (maior ou igual)

### Opcional: Buscando dados sob demanda com LazyDataModel do Primefaces

- Até então nossa paginação está sendo feita apenas em memória já que a lista de livros ainda está sendo carregada por completo. O ideal é que, para melhorar a performance, realizemos a paginação também no banco de dados. Assim carregamos os produtos somente que iremos ver naquele momento.

- Para fazer isso o Primefaces possui um componente chamado LazyDataModel. Que é responsável por buscar uma quantidade (definida pelo rows) de instâncias de um modelo (no nosso caso Livro). Nossa missão agora é alterar um pouco nosso código e fazer uso desse magnífico componente :). Vamos lá!

- 1) Crie uma classe no pacote br.com.caelum.livraria.modelo com o nome LivroDataModel que herde da classe LazyDataModel. A classe deve ser tipada com generics com o tipo do nosso modelo (o Livro):
```
public class LivroDataModel extends LazyDataModel<Livro> {
}
```
-  Precisamos dizer ao LazyDataModel qual é o valor máximo de registros que possuímos de livros. Vamos fazer isso em seu construtor chamando o método setRowCount() passando a quantidade. Podemos usar o método contaTodos do nosso DAO genérico.
```
public LivroDataModel() {
    super.setRowCount(???);
}
```
- Vamos precisar buscar a quantidade de livros que possuímos no banco e para isso vamos precisar instanciar nosso DAO genérico dentro da classe LivroDataModel. Além disso, crie um método quantidadeDeElementos dentro da classe DAO com a seguinte implementação:

```
// classe DAO
public int quantidadeDeElementos() {
    EntityManager em = new JPAUtil().getEntityManager();
    long result = (Long) em.createQuery("select count(n) from " + classe.getSimpleName() + " n")
            .getSingleResult();
    em.close();

    return (int) result;
}
```
- O construtor da classe LivroDataModel ficará:
```
public LivroDataModel() {
    super.setRowCount(dao.quantidadeDeElementos());
}
```
- 3) Precisamos também sobreescrever o método load que será responsável por buscar uma quantidade de livros no banco de dados e devolvê-la ao dataTable:
```
@Override
public List<Livro> load(int inicio, int quantidade, String campoOrdenacao, SortOrder sentidoOrdenacao, Map<String, Object> filtros) {

}
```
- Para buscar a lista paginada, a JPA nos ajuda através dos métodos setFirstResult e setMaxResult da interface Query. Porém já criamos um método que faz busca paginada para você :). Ele se chama listaTodosPaginada e está dentro do DAO genérico:

```
@Override
public List<Livro> load(int inicio, int quantidade, String campoOrdenacao, SortOrder sentidoOrdenacao, Map<String, Object> filtros) {
    return dao.listaTodosPaginada(inicio, quantidade);
}
```
- 4) Crie um atributo do tipo LivroDataModel dentro da classe LivroBean. (Não esqueça de criar o getter para ele)
```
public class LivroBean implements Serializable {
    // outros atributos

    private LivroDataModel livroDataModel = new LivroDataModel();

    // outros atributos
    // getters setters
}
```

- 5) Por último, altere o valor do atributo value do componente p:dataModel para realizar o binding com o LivroDataModel. Além disso, coloque lazy=true:

```
<h:form id="formTabelaLivros">
    <p:dataTable value="#{livroBean.livroDataModel}" var="livro" id="tabelaLivros" paginator="true" rows="5" lazy="true">
```

- Reinicie o servidor, a paginação deve continuar funcionando. Porém dessa forma, buscamos os dados no banco conforme precisamos deles :) Mas ainda temos um problema: precisamos alimentar o filtro, ou seja, buscar os livros pelo título. Como fazer?
- Podemos receber os valores que foram entrados pelo usuário nos filtros através do último parâmetro do método load da classe LivroDataModel:

```
@Override
public List<Livro> load(int inicio, int quantidade, String campoOrdenacao, SortOrder sentidoOrdenacao, Map<String, Object> filtros) {
    String titulo = (String) filtros.get("titulo");        
    return dao.listaTodosPaginada(inicio, quantidade);
}
```
- Vamos alterar um pouco o método listaTodosPaginada para aplicar o filtro na busca:
```
// classe DAO
public List<T> listaTodosPaginada(int firstResult, int maxResults, String coluna, String valor) {
    EntityManager em = new JPAUtil().getEntityManager();
    CriteriaQuery<T> query = em.getCriteriaBuilder().createQuery(classe);
    Root<T> root = query.from(classe);

    if(valor != null)
        query = query.where(em.getCriteriaBuilder().like(root.<String>get(coluna), valor + "%"));

    List<T> lista = em.createQuery(query).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();

    em.close();
    return lista;
}
```
- Estamos usamos a Criteria API da JPA 2.0 para implementar essa busca. Caso queira aprender mais sobre ela pode assistir nosso treinamento de JPA.
- Por fim, no método load vamos passar os novos argumentos ao método listaTodosPaginada:
```
@Override
public List<Livro> load(int inicio, int quantidade, String campoOrdenacao, SortOrder sentidoOrdenacao, Map<String, Object> filtros) {
    String titulo = (String) filtros.get("titulo");

    return dao.listaTodosPaginada(inicio, quantidade, "titulo", titulo);
}
```

<a name="anc6"></a>

## Melhorando a navegação com menus
- Navegação com p:menuButton
- Adicione a lib:
```xmlns:p="http://primefaces.org/ui"```
- Adicione o formulário
```
<h:form>
  <p:menuButton value="Menu">
    <p:menuitem value="Livros" action="livro?faces-redirect=true"  />
    <p:menuitem value="Autores" action="autor?faces-redirect=true"  />
    <p:separator />
    <p:menuitem value="Logout" action="#{loginBean.deslogar}"  />
  </p:menuButton>
</h:form>
```
- `p:growl` é um componente que mostrar as mensagens de maneira sobreposta (overlay) na tela. É uma forma mais elegante de notificar o usuário da sua aplicação.
```    <p:growl id="growl" showDetail="true" autoUpdate="true" /> ```

- **IMPORTANTE**
- ELIMINA O ERROR: java.lang.ClassCastException: com.sun.faces.facelets.compiler.UIInstructions não pode ser convertido para org.primefaces.model.menu.MenuElement
- Aidcione isso ao `web.xml`
```
  <context-param>
   <param-name>facelets.SKIP_COMMENTS</param-name>
   <param-value>true</param-value>
</context-param>
```

<a name="anc7"></a>

## Aplicando temas

<a name="anc8"></a>

## Gráfico com Primefaces
