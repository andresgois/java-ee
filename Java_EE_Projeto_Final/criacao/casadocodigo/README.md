# Java EE parte 1: crie sua loja online com CDI, JSF, JPA

- [Java EE parte 2: Sua loja online com HTML, REST e Cache](./README2.MD)

- [Java EE parte 3: Finalizando sua loja com REST, JMS, JAAS e WebSockets](./README3.MD)

### Links
- [Criando e Configurando o Projeto](#anc1)
- [Relacionando Livro com Autores](#anc2)
- [Validando e Exibindo Mensagens no Formulário](#anc3)
- [Data de Publicação e Converters](#anc4)
- [Adicionando e Exibindo a Capa do Livro](#anc5)
- [Criando a Home e o Detalhe do Livro](#anc6)

- [Site](http://localhost:8080/casadocodigo/index.xhtml)
##

<a name="anc1"></a>

## Criando e Configurando o Projeto
- Abra o seu Terminal e navegue até seu workspace. Dentro do seu workspace, aponte o terminal para o local onde você descompactou o forge. 
- No meu caso: `../forge/bin/forge`

- Para criar o projeto: `project-new --named casadocodigo`
- Passos a seguir
    - digite br.com.casadocodigo.
    - Enter na versão
    - Enter no final name
    - Enter na Location
    - Type project `war`
    - Build system `maven`
    - Aparecerá no terminal o nome do seu projeto
        - Digite:
            - **Arquivo de configuração do JSF**
            - faces-setup --facesVersion 2.2
            - **Arquivo de configuração do CDI**
            - cdi-setup --cdiVersion 1.1
- Importe o projeto no eclipse
- Adicione o wildfly 10
    - Configure-o para usar o standalone-full, pois tem mais recursos como gerenciamentos do EJB's.
    - adicione a lib do wildfly ao build path do projeto.

- [Acesse](http://localhost:8080/casadocodigo)
- [Form](http://localhost:8080/casadocodigo/livro/form.xhtml)

> Para que serve o JBoss Forge?
- Ele gera toda a estrutura base do projeto integrada com o Maven. Configuração de dependências (bibliotecas e frameworks), estrutura de pastas e gera configurações iniciais que geralmente envolve arquivos XML.

#### As tags padrão de propriedades, que seguem o formato chava / valor, ficando

- hibernate.hbm2ddl.auto onde pedimos para o Hibernate criar e manter nosso banco atualizado de acordo com as entidades, através do valor update;
- hibernate.show_sql pedimos para o Hibernate exibir o SQL que ele gera, através do valor true;
- hibernate.format_sql pedimos para o SQL exibido na configuração anterior, ser formatdo bonitinho através do valor true;
- hibernate.dialect dizemos ao Hiberante o dialeto do banco, para ele criar Queries próprias para aquele banco de dados;

### Configurando o Datasource
```
<datasource jndi-name="java:jboss/datasources/casadocodigoDS" pool-name="casadocodigoDS">
    <connection-url>jdbc:mysql://localhost:3306/casadocodigo_javaee</connection-url>
    <connection-property name="DatabaseName">
        casadocodigo_javaee
    </connection-property>
    <driver>mysql</driver>
    <pool>
        <min-pool-size>10</min-pool-size>
        <max-pool-size>20</max-pool-size>
    </pool>
    <security>
        <user-name>root</user-name>
    </security>
</datasource>
```

- Começando pela tag <datasource>, temos o atributo jndi-name, que é a ligação entre o datasource que estamos criando e a nossa aplicação;
- Mais abaixo, temo a tag <connection-url> que recebe o endereço do nosso banco de dados;
- Em seguida temos a tag <connection-property>, que obrigatoriamente precisamos adicionar quando usamos o Wildfly 10. Perceba que estamos informando algo que já está na tag connection-url, porém não será levado em cosideração - lá, apenas aqui na tag <connection-property>;
Na tag <pool> nós informamos o número mínimo e máximo de conexões que o datasource poderá criar com o banco de dados;
- Por fim, temos a tag <security>, onde informamos o nome do usuário do banco de dados na tag <user-name>, no nosso caso root; Caso você possua uma senha para acessar seu mysql, basta adicionar a tag <password> abaixo de user-name;

### Driver
- Parece que pulamos uma tag, que foi a <driver>. Essa tag referencia outra área especifica dentro da configuração, que fica logo abaixo de <datasource>, é a tag <drivers>. Dentro dela temos a tag <driver> de exemplo, porém, assim como em <datasource>, precisamos criar a nossa configuração de driver. Ficando nosso código, assim:
```
<driver name="mysql" module="com.mysql">
    <datasource-class>com.mysql.jdbc.jdbc2.optional.MysqlDataSource</datasource-class>
</driver>
```
- Essa configuração da tag <driver>, serve principalmente para informar a classe do datasource, o qual fazemos através da tag <datasource-class> do código acima. Mas ainda dentro da tag, temos um atributo um atributo importante, é o module. Todo driver JDBC, precisa de um JAR. Esse JAR não fica armazenado no projeto e sim no servidor, para ser mais especifico, no Wildfly os drivers ficam armazenados na pasta: wildfly/modules/. Devemos 
colocar nosso JAR dentro desta pasta, com a estrutura a seguir:

```
com/
    mysql/
        main/
            mysql-connector-java-5.1.35.jar
```

- [Link driver Mysql](http://central.maven.org/maven2/mysql/mysql-connector-java/5.1.35/mysql-connector-java-5.1.35.jar)

- Para finalizar, só precisamos criar o banco de dados, e faça os seguintes comandos:
    - acesse o Mysql: no terminal faça : mysql -u root
    - create database casadocodigo_javaee; para criar o banco.

> Qual a vantagem de utilizarmos o CDI no JavaEE? O que ganhamos com seu uso no projeto?
- Com o CDI ganhamos injeção de dependências entre as classes da aplicação e também o controle de escopo, que nos permite manter uma classe viva pelo tempo que precisarmos.

### Ferramentas utilizadas
- [JBOSS Forge](https://forge.jboss.org/download)
- [Wildfly](https://www.wildfly.org/downloads/)

<a name="anc2"></a>

## Relacionando Livro com Autores
- Adicione a div e o <h:selectManyListbox> referente a seleção dos autores, exibindo o nome do autor e relacionando o valor do select pelo id do autor. Também já vamos colocar o converter javax.faces.Integer para que os ID sejam enviados como Integer e não String.

- Insert de autores
```
insert into Autor (nome) values ('Paulo Silveira'), ('Sérgio Lopes'), ('Guilherme Silveira'), ('Alberto Souza');
```

### Converters
- Todos os valores que são enviados da tela para o ManagedBean são enviados como texto. O JSF não consegue converter automaticamente, o que seria possível fazer em uma lista. O generics não é identificado pelo JSF, o que nos obriga a fazer uso de conversores que explicitamente convertem o valor de texto para inteiro. Assim, sempre que utilizamos uma lista é importante converter os valores para o tipo que precisamos com os converters do JSF.

- Lembre-se de implementar o converter na sua view, ele vai ficar parecido com isso:

```
<h:selectManyListbox value="#{adminLivrosBean.autoresId}" converter="javax.faces.Integer">
    <f:selectItems value="#{adminLivrosBean.autores}"
         var="autor"
         itemValue="#{autor.id}"
         itemLabel="#{autor.nome}" />
</h:selectManyListbox>
```

- [Doc Converters](https://docs.oracle.com/javaee/7/tutorial/jsf-page-core001.htm)

### Problema ao redicionar
- Ao redicionar do form para a lista e atualizar a página, faz o reenvio do form
- Para resolver isso:
- Resolveremos isto através de recursos do protocolo HTTP chamado de redirect. O redirect passa um http status - - - para o navegador carregar uma outra página e esquecer dos dados da requisição anterior. O http status que o navegador recebe é um 302.

- Para isso devemos mudar o retorno do método salvar, que além do caminho que já retornava, retornará um parametro informando ao JSF para enviar um redirect ao navegado.

```
return "/livros/lista?faces-redirect=true";
```

> Para que ela serve e qual e quem é responsável por ela no JavaEE?
- Garante que os dados enviados para o banco de dados serão salvos, abrindo e fechando a transação no momento correto. O JTA é a especificação responsável pelas transações no JavaEE.

<a name="anc3"></a>

## Validando e Exibindo Mensagens no Formulário
- O JSF já tem um objeto responsável por mensagens, que é o FacesMessages. Temos que pegar este objeto e adicionar nele a nossa mensagem. Para adicionar uma mensagem no FacesMessage vamos precisar fazer uso de um outro objeto do JSF, o FacesContext, que é um objeto que nos dá todo o contexto do JSF e nos permitirá adicionar nossa mensagem, assim, usamos o FacesContext para adicionar um FacesMessage

```
@Transactional
public String salvar() {
      for (Integre autorID : autoresId) {
        livro.getAutores().add(new Autro(autorId));
    }
    dao.salvar(livro);
    FacesContext.getCurrentInstance()
        .addMessage(null, new FacesMessage("Livro cadastrado com sucesso!"));

    return "/livros/lista?faces-redirect=true";
  }

```
- O JSF e qualquer framework MVC atual possui um escopo especial (muito rápido) que chamamos de Flash Scope. O Flash Scope começa em um request e termina no request seguinte.

- Indo um pouco mais a fundo no Flash Scope, ele consegue aumentar o tempo de vida de um objeto usando a sessão do usuário, adicionando no primeiro request o objeto na sessão e ao fim do segundo request o próprio JSF se encarrega de remover o objeto da sessão. Para fazer uso do Flash Scope, precisamos setar uma informação no contexto do JSF. Dentro do método salvar, faremos o seguinte:

```
    // chamada do livroDao.salvar acima
    FacesContext.getCurrentInstance().getExternalContext()
        .getFlash().setKeepMessages(true); // Aqui estamos ativando o FlashScope
    FacesContext.getCurrentInstance()
        .addMessage(null, new FacesMessage("Livro cadastrado com sucesso!"));
```

> De que serve o Escopo de Flash que temos no JSF?
- Serve para guardar valores na sessão, porém duram apenas de um request para o outro, sendo automaticamente retirados da sessão.

### Ensinando o CDI a instanciar o FacesContext
- O problema é que o CDI e o JSF ainda não estão totalmente integrados e, por isso, a solução não funciona diretamente. Para que funcione, indicaremos para o CDI como criar o objeto que será injetado em nosso código. Por hora, ele não sabe criar o context sozinho, mas podemos ensiná-lo.

- Vamos criar uma nova classe chamada FacesContextProducer no pacote conf. Nesta classe, teremos o método getFacesContext que retornará uma instância de FacesContext para cada request, ou seja, no escopo da requisição.

- Isto é tudo que a nossa classe que cria objetos de contexto precisa fazer, porém precisamos indicar para o CDI que este método faz exatamente isso: produz um objeto. Para isso utilizamos a anotação @Produces.

```
br.com.casadocodigo.loja.conf

public class FacesContextProducer{

        @RequestScoped
        @Produces
        public FacesContext getFacesContext(){
                return FacesContext.getCurrentInstance();
        }

}
```

### Validações
- Faremos então uma validação especifica que o Java EE já possui e que é feita diretamente na classe Java, esta validação é chamada de Bean Validation. A Bean Validation é uma especificação do JavaEE e toda especificação precisa ter uma implementação, o WildFly já tem um framework que implementa esta especificação internamente no próprio servidor, que é o Hibernate Validator.

- Usando o Hibernate Validator, quando queremos dizer que o Título não pode ser nulo, nem vazio e nem possuir espaços em branco, usamos a annotation @NotBlank em cima do atributo.

- Porém, existem outras coisas que queremos validar, por exemplo, o valor mínimo aceito pelo preço e número de páginas, o tamanho mínimo preenchido para o campo descrição, e por aí vai. Para vários desses casos, temos annotations específicas da Bean Validation, e para os casos não cobertos pela Bean Validation o Hibernate Validator veio para cobrir.

- As mensagens ligadas aos componentes do JSF estão melhores, porém as mensagens de validação da Bean Validation ainda estão genéricas. Seria muito bom poder deixá-las mais específicas para nosso sistema. Lembrando que elas só aparecem em português por que o Hibernate Validator possui internacionalização para várias línguas, porém mesmo assim, ainda não está como gostaríamos.

- Vamos acessar o [endereço](http://bit.ly/1DsZKvf) . Nesse endereço, encontramos o arquivo .properties do Hibernate Validator. Como queremos exatamente esses nomes, vamos copiar todas as mensagens e salva-las em um novo arquivo dentro da pasta src/main/resources/.

- Validação no JSF é ligada ao componente porém possui algumas limitações a tipos de validações que o JSF já possui, enquanto que na Bean Validation temos uma variedade maior de validações, inclusive para regras de negócio como CPF, Título de Eleitor e até número do cartão de crédito. Por isso a Bean Validation acaba sendo mais poderosa e você ainda fica desacoplado do JSF.


<a name="anc4"></a>

## Data de Publicação e Converters

### Datas
- Na entidade de Livri
    - Já incia com a data atual
```
@Temporal(TemporalType.DATE)
private Calendar dataPublicacao = Calendar.getInstance(); 
```

- O JSF não conhece o formato das datas
```
<div>
    <h:outputLabel value="Data de Publicação" />
    <h:inputText value="#{adminLivrosBean.livro.dataPublicacao.time}"
            id="dataPublicacao">
        <f:convertDateTime pattern="dd/MM/yyyy" timeZone="America/Sao_Paulo"/>    </h:inputText>
    <h:message for="dataPublicacao" />
</div>
```
### Criando o Proprio converter
- Deixamos nosso campo de data normal com um input
```
<!-- Só o input simples -->
<h:inputText value="#{adminLivrosBean.livro.dataPublicacao}"
        id="dataPublicacao" />
```

- Esses dois métodos funcionam assim. Quando a informação está na tela, ela é uma String, nesse ponto o JSF chama o método **getAsString()** do nosso Converter. Quando está no ManagedBean é um Objeto que queremos, desta forma o JSF chama o método **getAsObject()**.
```
@FacesConverter(forClass=Calendar.class)
public class CalendarConverter implements Converter {

    private DateTimeConverter converter = new DateTimeConverter();

    public CalendarConverter() {
        converter.setPattern("dd/MM/yyyy");
        converter.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));
    }

    @Override
    public Object getAsObject(FacesContext context,
                UIComponent component, String dataTexto) {
        Date data = (Date) converter.getAsObject(context, component, dataTexto);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(data);
        return calendar;
    }

    @Override
    public String getAsString(FacesContext context,
                UIComponent component, Object dataObject) {
        if (dataObject == null)
            return null;

        Calendar calendar = (Calendar) dataObject;
        return converter.getAsString(context, component, calendar.getTime());
    }
}
```

- O que mais ganhamos com essa implementação do Converter? Ganhamos o uso de Calendar em qualquer entidade do sistema, sendo transformada para texto automaticamente pelo JSF, sem que tenhamos que nos preocupar com o formato e timeZone. Criamos um único objeto que já serve para todo o sistema.

### Converter para Autor
- Começaremos pelo método getAsObject()... Queremos recuperar o autor como um objeto, então faremos new Autor e da instância obtida, chamaremos o setId que recebe o id - basta transformar de String para Integer com Integer.valueOf. Já temos a ideia principal, só iremos verificar antes, se a String recebida não é nula ou vazia. O método ficará assim:


```
public Object getAsObject(FacesContext context, UIComponent component, String id) {
    if (id == null || id.trim().isEmpty()) 
        return null;

    Autor autor = new Autor();
    autor.setId(Integer.valueOf(id));

    return autor;
}
```
- Seguiremos para o método getAsString() que fará justamente o inverso. Queremos recuperar o id do autor no formato String. Já recebemos o objeto Autor como Object, agora, faremos um casting de volta para Autor. Deste, chamaremos o getId e depois o toString() - para transformá-lo em texto. Se o objeto recebido estiver null, teremos uma NullPointerException, então, evitaremos esse caso desde o início do método.

```
public String getAsString(FacesContext context, UIComponent component, Object autorObject) {
    if (autorObject == null)
        return null;

    Autor autor = (Autor) autorObject;
    return autor.getId().toString();
}
```



<a name="anc5"></a>

## Adicionando e Exibindo a Capa do Livro
- É precisamos dizer no nosso formulário que queremos enviar arquivo por ele e não em formato de texto. Para isso, no formulário, adicionaremos o atributo enctype dentro do form. O código ficará assim:
```
<h:form enctype="multipart/form-data">
```
- Desejamos transferir arquivos, mas, anteriormente tínhamos que trabalhar com base em arrays de bytes ou FileInputStream - tudo feito manualmente. O JavaEE 7 nos trouxe um novo objeto que já tem a capacidade de salvar um arquivo dentro dele: o Part. Usaremos o tipo Part e, no método salvar(), realizaremos a transferência do arquivo recebido pelo formulário para o sistema operacional.

- O tipo **Part** possui um método chamado write() que recebe uma String com o caminho onde queremos salvar o arquivo dentro do S.O.. Por isso, vamos passar o seguinte caminho: /casadocodigo/livros/, concatenando a isso o nome original do arquivo que nos foi enviado e pode ser obtido pelo código arquivo.getSubmittedFileName().

```
public class AdminLivrosBean {
    // Os demais atributos ficam aqui, não alterar!
    private Part capaLivro;
    @Transactional
    public String salvar() throws IOException {
        dao.salvar(livro);
        capaLivro.write("/casadocodigo/livros" + capaLivro.getSubmittedFileName());
        // Mantenha o restante do método aqui
    }
    // Demais getter's e setter's, não alterar
}
```

### Criando um servlet
- Para que o sistema pegue esse arquivo de dentro do S.O., precisaremos tratar isso dentro da aplicação. Faremos isto, utilizando Servlet normal da especificação de Servets. Em seguida, criaremos uma nova classe no pacote br.com.casadocodigo.servlets e daremos o nome da classe de FileServlet. Faça a nova classe herdar de HttpServlet e já sobrescrever o método service(). Todo Servlet precisa de um mapeamento, no nosso caso, faremos via annotation usando o @WebServlet e informando o caminho que desejamos mapear. No nosso caso, usaremos o mapeamento /file.

- Um detalhe interessante é a forma como nosso servlet será chamado. Do jeito que fizemos até aqui, para chamar nosso Servlet, precisaremos passar algum parâmetro com o nome do arquivo que queremos carregar, por exemplo:

`http://localhost:8080/casadocodigo/file?p=livros/java-8-featured_large.png`
- Mas desta forma, o carregamento ficará estranho . Seria melhor fazer:

`.../file/livros/java-8-featured_large.png`

- Assim, ficaria parecendo que estamos acessando realmente o arquivo. Conseguiremos esse resultado simplesmente adicionado um /* (barra asterisco) no fim do mapeamento já feito. Assim a base de nosso Servlet ficará assim:

```
@WebServlet("/file/*")
public class FileServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse res) 
                throws ServletException, IOException {
        // código do tratamento do arquivo irá aqui!
    }

}
```
- Dentro do método service(), conseguimos pegar o que vier pelo * por meio do objeto req.getRequestURI(). O método nos retorna toda a URL, mas só nos interessa o que vier depois de /file, então faremos um split("/file") e do Split teremos dois lados, valor [0] será o que vem antes do /file e [1] o que vem depois. Queremos este último, uma vez que é isso que temos salvo no banco de dados. Desta forma, já teremos o path.

`String path = req.getRequestURI().split("/file")[1];`

- Para que o arquivo seja enviado no response do Servlet, precisaremos informar o contentType do arquivo. Neste caso específico, sabemos que se trata de uma imagem, porém queremos deixar o FileServlet mais genérico. Usaremos a API nova do Java de NIO para pegar o contentType direto do arquivo Paths.get("caminho completo do arquivo"). No entanto, temos que acessar de fato o arquivo e, até o momento, só temos o caminho relativo do arquivo. Onde foi que guardamos o arquivo dentro do servidor?

- Podemos juntar o path que temos com o caminho fixo do nosso FileSaver para conseguirmos o caminho completo do arquivo. Passaremos as duas informações para o Paths e assim obtemos um Path como sendo a fonte do nosso arquivo.

`Path source = Paths.get(FileSaver.SERVER_PATH + "/" + path);`

- O Path servirá como fonte para o FileNameMap conseguir chegar no arquivo e obter o contentType. O FileNameMap pode ser obtido usando a classe URLConnection chamando o método estático da classe getFileNameMap.
`FileNameMap fileNameMap = URLConnection.getFileNameMap();`

- Pelo fileNameMap, chamaremos o método getContentTypeFor() passando nosso source. Só temos antes que informar que o protocolo de acesso é file: para que o FileNameMap possa pegar o arquivo corretamente. Assim sendo, temos:
`String contentType = fileNameMap.getContentTypeFor("file:"+source);`

- Agora sim, poderemos setar o contentType no nosso response:
`res.setContentType(contentType);`

- Mas parece que tivemos tanto trabalho apenas para setar um valor no response. Será que valeu a pena?

- Todo navegador verifica sempre o Header da resposta do servidor para saber o que ele deve fazer. Quando você abre um PDF no Chrome por exemplo, ele possui um leitor interno de PDF's, e já é possível ao usuário ler o arquivo sem ter que abri-lo no seu computador na mão. Isso vale para outros tipos de arquivos, imagens, vídeos, e etc. Assim, é muito importante dizer ao navegador qual o tipo de conteúdo que estamos enviando para ele, e ele se ajustará a esse tipo de conteúdo.

- Outro Header que é importante informar, é o tamanho do arquivo ou Content-Length, o que também ajuda o navegador a baixar corretamente o arquivo. Usaremos outra classe da API de NIO do Java, que é a classe Files.
`res.setHeader("Content-Length", String.valueOf(Files.size(source)));`

- E por fim, o Header que coloca o nome correto do arquivo que estamos baixando, que é o Content-Disposition.

```
res.setHeader("Content-Disposition", 
                "filename=\""+source.getFileName().toString() + "\"");
```

- Ainda usaremos esse Content-Disposition mais adiante. Por enquanto, são esses os Headers que precisamos informar. Parece trabalhoso fazer tudo isso manualmente, mas não temos escolha... Até o momento, o JavaEE não possui nenhuma forma de obter esses dados automaticamente.

- Usando JSF ainda temos mais uma coisa a fazer, que é limpar o response antes de setar qualquer cabeçalho. Lembre-se que o JSF pode ter recebido esse request e assim, já podemos ter colocado alguma informação no response, por isso é importante limpá-lo sempre que usarmos o response, evitando resultados inesperados.

```
res.reset();
// Antes de setar qualquer Header.
```

- Agora estamos prontos. Mas ainda não transferimos o arquivo de fato, apenas preparamos o response para isso. Usaremos o FileSaver que já temos e ele cuidará da operação. Como queremos transferir o arquivo do servidor para o response, criaremos um método estático dentro de FileSaver chamado transfer(). Esse método tem a seguinte assinatura:

```
public static void transfer(Path source, OutputStream outputStream) {
    // código que manipula o arquivo
}
```

- Já podemos chamá-lo no FileServlet, então vamos adicionar a chamada dele e logo depois veremos como implementar a transferência. Nosso método service() do Servlet ficou assim:

```
protected void service(HttpServletRequest req, HttpServletResponse res) 
            throws ServletException, IOException {
    String path = req.getRequestURI().split("/file")[1];

    Path source = Paths.get(FileSaver.SERVER_PATH + "/" + path);
    FileNameMap fileNameMap = URLConnection.getFileNameMap();
    String contentType = fileNameMap.getContentTypeFor("file:"+source);

    res.reset();
    res.setContentType(contentType);
    res.setHeader("Content-Length", String.valueOf(Files.size(source)));
    res.setHeader("Content-Disposition", 
            "filename=\""+source.getFileName().toString() + "\"");
    FileSaver.transfer(source, res.getOutputStream());
}
```

- Agora, voltando ao nosso FileSaver, o primeiro passo da transferência é realizar a entrada do arquivo, do servidor para o sistema. Usaremos a classe FileInputStream para isso. Passaremos o FileInputStream para a classe Channels.newChannel que abre um canal direto com o arquivo, retornando o objeto ReadableByteChannel.

- Além do canal de entrada, precisaremos de um canal de saída. Vamos usar o Channels.newChannel novamente e passaremos o OutputStream que recebemos como parâmetro. O canal nos retornará um WritableByteChannel.

- Como os dois recursos ReadableByteChannel e WritableByteChannel são canais ligados direto ao arquivo e ao response do servidor, precisaremos fechar os recursos. Desde o Java 7, temos a possibilidade de usar o try-with-resources que automaticamente já fecha um recurso após o fim do try. O código ficará assim:

```
FileInputStream input = new FileInputStream(source.toFile());
try( ReadableByteChannel inputChannel = Channels.newChannel(input);
            WritableByteChannel outputChannel = Channels.newChannel(outputStream)) {
    // código que transfere o arquivo.
}
```

- Além da entrada e saída, a transferência de um lado para o outro deve ser feita sempre usando um Buffer. Nesta, os arquivos serão transferidos em pedaços. No caso, vamos transferir 10kb por vez. Para criar nosso Buffer usaremos a classe ByteBuffer.allocateDiret passando como parâmetro 1024 * 10 que representa os 10Kb.

```
public static void transfer(Path source, OutputStream outputStream) {
    try {
        FileInputStream input = new FileInputStream(source.toFile());
        try( ReadableByteChannel inputChannel = Channels.newChannel(input);
                WritableByteChannel outputChannel = Channels.newChannel(outputStream)) {
            ByteBuffer buffer = ByteBuffer.allocateDirect(1024 * 10);

        } catch (IOException e) {
            throw new RuntimeException(e); 
        }
    } catch (FileNotFoundException e) {
        throw new RuntimeException(e); 
    }
}
```

- Vamos começar a ler do nosso canal de entrada e transferir para o buffer. Faremos isto, enquanto existirem bytes para serem lidos. Usaremos um while para isso, e o próprio inputChannel possui um método chamado read() que nos retornará o valor -1 quando não houver mais bytes a serem lidos.
```
while(inputChannel.read(buffer) != -1) {
    outputChannel.write(buffer);
    buffer.clear();
}
```

- Depois de adicionarmos o while(), o trecho ficará da seguinte maneira:

```
public static void transfer(Path source, OutputStream outputStream) {
    try {
        FileInputStream input = new FileInputStream(source.toFile());
        try( ReadableByteChannel inputChannel = Channels.newChannel(input);
                  WritableByteChannel outputChannel = Channels.newChannel(outputStream)) {
              ByteBuffer buffer = ByteBuffer.allocateDirect(1024 * 10);

        while(inputChannel.read(buffer) != -1) {
            outputChannel.write(buffer);
            buffer.clear();
        }
        } catch (IOException e) {
            throw new RuntimeException(e); 
        }
    } catch (FileNotFoundException e) {
        throw new RuntimeException(e); 
    }
}
```

- Com os bytes no buffer, podemos enviar para o outputChannel, só que dessa vez não queremos ler, e sim escrever na saída. Faremos isto, usando o método write() e passando para ele o buffer com os bytes lidos. Depois que escrevermos no buffer, queremos que ele seja limpo usando o buffer.clear(). Desta forma, ele poderá voltar a ler mais informações do arquivo.

> Como ficou facilitado o upload de arquivos no JavaEE 7?
- Através da tag <h:inputFile> que envia o arquivo selecionado para um objeto do tipo Part, facilitando a manipulação de arquivos no Bean.


<a name="anc6"></a>

## Criando a Home e o Detalhe do Livro

- [Lista](http://localhost:8080/casadocodigo/livro/lista.xhtml)
- [index](http://localhost:8080/casadocodigo/index.xhtml)

-  JSF carregar os detalhes do Livro antes de prosseguir com a página. Ainda dentro de <f:metadata>, coloque uma nova tag chamada <f:viewAction> com o atributo action apontando para #{livroDetalheBean.carregaDetalhe()}.

```
<f:metadata>
    <f:viewParam id="id" name="id" value="#{livroDetalheBean.id}"/>
    <f:viewAction action="#{livroDetalheBean.carregarDetalhe()}"/>
</f:metadata>
```

-  Erro
    - Caused by: org.hibernate.LazyInitializationException: failed to lazily initialize a collection of role: br.com.casadocodigo.loja.models.Livro.autores, could not initialize proxy - no Session
- Resolução
- Antes

```
public Livro buscarPorId(Integer id) {
    return manager.find(Livro.class, id);
}
 
```
- Depois

```
public Livro buscarPorId(Integer id) {
		String jpql = "select l from Livro l join fetch l.autores "
				+ " where l.id = :id";
		return manager.createQuery(jpql, Livro.class)
				.setParameter("id", id)
				.getSingleResult();
	}
 
```

- Para esse caso, precisamos fazer uso de um recurso que temos na especificação do JPA, que estende o contexto por mais tempo do que o normal, também conhecido como Extended Entity Manager.

- Para ativar o contexto estendido do Entity Manager, vamos abrir nosso LivroDao, e dentro da annotation **@PersistenceContext** vamos colocar um atributo chamado type com o valor PersistenceContextType.EXTENDED. Pela especificação do JavaEE, para usar o EXTENDED, o nosso LivroDao precisará ser um EJB do tipo Statefull. Essa é uma dependência da especificação.

- Assim, nosso LivroDao ficará assim:
```
@Stateful
public class LivroDao {

    @PersistenceContext(type=PersistenceContextType.EXTENDED)
    private EntityManager manager;

    // demais métodos abaixo
}
```

- Essa annotation @Stateful torna nossa classe um Enterprise Java Bean. Os EJB's eram o centro das especificações JavaEE até a versão 6.0, quando chegou o CDI para ser essa camada intermediária que liga as partes do nosso sistema com o servidor.

- Desde então o pessoal da Oracle vem tirando o poder concentrado nos EJB's e dividindo em pequenas outras especificações como a própria JPA, CDI, JMS, JTA e etc.

- Dessa forma, estamos resolvendo o problema da conexão ser fechada, já que o incrementamos o contexto do Entity Manager. Essa é outra forma que temos de resolver o LazyInitializationException. Mas junto disso ganhamos mais uma coisa. Por exemplo, da forma como estamos fazendo, verifique no console a quantidade de selects que o Hibernate está fazendo para nós, e percebemos que temos apenas um select já com o join dos autores:

- com isso, 2 selects, um em autor e outro no livro
```
public Livro buscarPorId(Integer id) {
        return manager.find(Livro.class, id);
}
```

![Quantidade de selects](../asserts/join-fetch-um-select.png)

- Assim, planejar suas queries sempre tem um ganho a mais, nesse caso, menos consultas até o banco de dados, tornando a aplicação mais performática. Por isso é bem importante saber usar os recursos que temos disponíveis nas especificações, sabendo que JSF e JPA, apesar de ser relativamente fácil começar a programar nessas especificações, é importante saber o que está sendo feito. Não saber o correto funcionamento de uma especificação e quando usar certos recursos ou não, pode ser motivo de grandes problemas futuros.

- Utilize os recursos do JavaEE com consciência e obtenha o máximo de produtividade no desenvolvimento do seu sistema.

- Melhor maneira, apenas um select
```
public Livro buscarPorId(Integer id) {
        String jpql = "select l from Livro l order by l.autores"
                        + "where l.id = :id";
        return manager.createQuery(jpql, Livro.class)
                .setFirstResult("id", id);
                .getSingleResult();

}
```
