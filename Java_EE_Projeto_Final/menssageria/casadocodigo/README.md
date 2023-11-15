# Java EE parte 3: Finalizando sua loja com REST, JMS, JAAS e WebSockets


### Links
-(#anc1)
-(#anc2)
-(#anc3)
-(#anc4)
-(#anc5)
-(#anc6)

-(http://localhost:8080/casadocodigo/index.xhtml)
##

<a name="anc1"></a>

## Disponibilizando lançamentos em XML e JSON 
### Disponibilizando dados no formato JSON
- Para disponibilizar os dados no formato JSON, por meio de um acesso a uma URL da aplicação (ou seja, utilizando o método HTTP GET)
- @GET, para indicar qual o método HTTP utilizado; @Path para indicar o caminho do recurso; @Produces({ MediaType.APPLICATION_JSON }, para indicar quando o formato que iremos disponibilizar.

### Problema ao retornar os dados em XML
```
Could not find MessageBodyWriter for response object of type: java.util.ArrayList of media type: application/xml
```
- Precisamos utilizar a anotação @XmlRootElement na classe do tipo que estamos retornando no método ultimosLancamentosXml().

- Como estamos retornando uma ArrayList e não temos como modificar o código da classe, poderíamos criar uma classe que tivesse um ArrayList como atributo e então utilizar a anotação.

- Mas como estamos utilizando o RESTEasy como implementação da JAX-RS, podemos utilizar a anotação @XmlRootElement no tipo do ArrayList. Então podemos utilizar a anotação na classe Livro e o RESTEasy irá resolver isto.

### Elemento raiz
- E elemento raiz do XML gerado, é <collection>
-(../../../asserts/005-collection-raiz.png)
- Mas baseado no padrão que normalmente é encontrado, esse elemento deveria ser <livros>. O que precisamos fazer para realizar esta alteração?
    - É necessário adicionar a anotação @Wrapped(element = "livros"), no método ultimosLancamentosXml().


<a name="anc2"></a>

## Envie e-mails para o usuário
```
public class MailSender {

	@Resource(mappedName = "java:/jboss/mail/gmail")
	private Session session;

	public void send(String from, String to, String subject, String body) {
		MimeMessage message = new MimeMessage(session);
		try {
			message.setRecipients(javax.mail.Message.RecipientType.TO, 
					InternetAddress.parse(to));
			message.setFrom(new InternetAddress(from));
			message.setSubject(subject);
			message.setContent(body, "text/html");
			
			Transport.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}	
}
```
- Crie um MimeMessage que recebe um Session no construtor.
- Iremos receber o Session como um recurso da aplicação. Utilize a anotação @Resource, passando o nome da JNDI que será associada ao recurso:
- **@Resources** por que não usar o @Inject?
    - Isso ocorreu porque ainda não é possível utilizar a anotação @Inject para injetar um componente quem vem de um recurso mapeado via JNDI.
    - sempre que agente usa um recurso que é capitado pelo JNDI o defaul do Java EE é usar o *resource*
    - já procura as configurações automáticas

- Para que a injeção do objeto Session definido na classe MailSender funcione, é necessário que você adicione algumas configurações no arquivo standalone-full.xml no Wildfly.

- Esse arquivo fica DIRETORIO-DO-WILDLY/standalone/configuration, e pode ser acessado pelo Eclipse, a aba Servers:
- Encontre a tag <mail-session> dentro do arquivo:

- Adicione a sua configuração de envio de e-mail. Para o nome de usuário e senha, adicione um e-mail e senha de alguma conta sua, para que você possa testar o envio de e-mails:

```
<subsystem xmlns="urn:jboss:domain:mail:2.0">
    <mail-session name="default" jndi-name="java:jboss/mail/Default">
        <smtp-server outbound-socket-binding-ref="mail-smtp" />
    </mail-session>
    <mail-session name="gmail" jndi-name="java:jboss/mail/gmail">
        <smtp-server outbound-socket-binding-ref="mail-smtp-gmail"
            ssl="true" username="SEU-NOME-DE-USUARIO" password="SUA-SENHA" />
    </mail-session>
</subsystem>
```
- O atributo outbound-socket-binding-ref faz referência a um "mail-smtp-gmail". Então precisamos adicionar essa configuração, onde vamos adicionar detalhes como o host e a porta utilizados pelo Gmail. Busque por outbound-socket-binding:
- E adicione o mail-smtp-gmail:
```
<socket-binding-group name="standard-sockets"
    <!-- código omitido -->
    <outbound-socket-binding name="mail-smtp">
        <remote-destination host="localhost" port="25" />
    </outbound-socket-binding>
    <outbound-socket-binding name="mail-smtp-gmail">
        <remote-destination host="smtp.gmail.com" port="465" />
    </outbound-socket-binding>
</socket-binding-group>
```

- No seu email, acesse a página de segurança e selecione a opção "Permitir aplicações menos seguras", para que seja possível enviar o e-mail através da nossa aplicação.

<a name="anc3"></a>

## Trabalhe Assincronamente com JMS

### JMS

- Dentro do JMS temos dois tipos de mensagens assíncronas que podemos enviar:

    - Tópico (topic): lista de discussão, como grupo de e-mail ou whatsapp, onde todos recebem a mesma mensagem.
    - Fila (queue): envia um e-mail para o primeiro da fila, o segundo poderá receber outra mensagem, diferente do Tópico.
- O /jms abre o contexto JMS no Wildfly, mas o /topics é aleatório, podendo ser substituído por, por exemplo, /topic ou /topico.
- Temos então o Destination sendo injetado e o envio da mensagem pelo producer.send(destination, compra.getUuid()). O próximo passo configuraremos quem irá ouvir (listener) a mensagem.

### Entendendo o message driven

- Vamos tentar capturar a mensagem que estamos enviando de maneira que possamos, de fato, enviar o e-mail. Para isso usaremos a Classe EnviaEmailCompra. Ela será bastante modificada em relação a como está agora. ela tem uma configuração diferente para que se consiga receber as mensagens. Já discutimos alguns conceitos do JMS, como o assíncrono que o servidor executa e aquele que liberamos o usuário para que outras operações sejam executadas em background. Focaremos agora em resolver de fato o listener. A primeira implementação será anotar a Classe com @MessageDriven():
- O pacote dele é "javax.ejb". O @MessageDriven() é um EJB especial através do qual conseguimos escutar uma determinada mensagem,podendo ser uma fila ou um tópico. Precisamos indicar quem ele vai, de fato, ouvir, ou seja, o Destination. Passamos o caminho já configurado anteriormente como a propriedade activationConfig e a anotation @ActivationConfigProperty com chave e valor. Este é o mesmo do @Resource:

```
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName="destinationLookup", propertyValue="java:/jms/topics/CarrinhoComprasTopico")
})
```
- O propertyValue é aquele que "ouviremos" e, como nesse caso estaremos ouvindo um tópico e para o EnviaEmailCompra não faz diferença aquilo que ele estará ouvindo, só precisamos realmente conseguir receber a mensagem. Mas como o @MessageDriven sabe que a Classe EnviaEmailCompra possui o método enviar(String uuid)? Não tem como saber, portanto precisamos implementar isso utilizando o MessageListener do pacote javax.jms:

### Diferença entre tópicos e filas
> Um message-driven bean escuta um destino que pode ser um tópico ou uma fila. Qual a diferença, se existir alguma?

- Um destino conhecido como tópico é uma forma de fazer com que a mensagem seja tratada por vários objetos.

> Qual anotação utilizamos para indicar que uma classe será um message driven bean e será capaz de receber mensagens?
- @MessageDriven

### Recebendo o e-mail assíncrono
- Falta-nos uma última configuração que é a do Destination, para que nosso servidor entenda que o destino é um tópico e para onde ele será enviado para todo mundo que se registra nele. Além do emissor/produtor da mensagem, temos também aquele que escuta a mensagem, o listener e precisamos de alguém no meio que faça essa comunicação. Precisamos lembrar que este é um processo assíncrono, ou seja, quem está enviando a mensagem não o faz diretamente para o receptor, mas sim para alguém que está no meio e recebe esta mensagem. Este que está no meio precisa saber quem precisa ouvir a mensagem. Quem precisar receber deverá avisar quem está no meio falando que quer receber, assim ele sai enviando para cada um dos listeners. É assim que funciona o JMS. Este destino central é conhecido como destination config ou JMS Destination.

- Para que isso funcione precisamos de uma Classe, que chamaremos de ConfigureJMSDestination
- Essa Classe de configuração não terá nenhum código, não temos como fugir disso. Estamos ganhando em configuração, uma vez que não precisamos abrir XML, mas alguns códigos ficam estranhos. Precisaremos configurar um @JMSDestinationDefinition, o qual será um array de definições:
- 
```
@JMSDestinationDefinition(
    name="java:/jms/topics/CarrinhoComprasTopico",
    interfaceName="javax.jms.Topic"
)
public class ConfigureJMSDestination {

}
```
- É no interfaceNameque avisamos que é um tópico de fato. Dessa forma temos nossa configuração de destino. Se subirmos o servidor aparecerão alguns erros, sendo um deles um WARN referenciando destinationType=null. Isso acontece porque também precisamos configurar o listener. Dentro do @MessageDriven fazemos:
- Antigamente o Wildfly utilizava um framework JMS de fila chamado HornetQ, porém ele foi passado para o grupo da Apache. Agora o Wildfly está usando o ActiveMQ.


- Todo JB tem que sair no console. ele é registrado e são criados alguns namespaces, ou JDI names, os quais são configurados no log do servidor, porém ainda não foram na Classe EnviaEmailCompra. O @MessageDriven é uma JDI name e deve ser registrado no log do servidor.

- Iremos forçar isso com a anotação @Stateless do javax.ejb, pois não queremos guardar o estado do compraDao. Como ele será injetado no PagamentoService. Vejamos se irá funcionar. Subimos novamente o servidor e dará outro erro. Teremos que usar a anotação @Singleton na Classe ConfigureJMSDestination.

- Vamos rodar novamente a aplicação, fazendo uma compra. Dessa vez conseguiremos rodar, mas por algum motivo a aplicação não está encontrando o ConfigureJMSDestination.

<a name="anc4"></a>

## Proteja sua aplicação com JAAS

### Protegendo a administração da aplicação
- Perceba que eu digitei a URL "livros/lista.xhtml". Nós poderíamos entrar no cadastro também, tanto faz, mas percebemos que isso nos dá acesso direto a nossa lista de livros. Não só a nossa lista de livros, como a cadastrar um novo livro.
- Para segurança na aplicação, temos no Java uma especificação que cuida exatamente dessa parte, uma especificação que cuida da auto indicação e da autorização, chama-se JAAS, Java authorizations authentication service ou authentication authorization, na verdade. Essa especificação trata de todo esse sistema de permissões, permissão de usuários, URL e tudo aquilo que podemos acessar para que a nossa aplicação tenha isso funcionando.
- Para que a nossa aplicação possa fazer uso do JAAS nós vamos precisar abrir o arquivo "web.xml". Em geral, toda configuração que vamos precisar fazer será através de XML e as que não forem XML, será apenas a parte de autenticação, ou seja, a tela de login.
- azendo com que ele identifique o usuário, mandamos isso para o JAAs e ele vai tomar conta de boa parte da nossa aplicação. No "web.xml" nós temos uma configuração bem simples de fazer chamada security-constraint, será o nosso endereçamento de segurança.
- O security-constraint no JAAS serve basicamente para definirmos duas coisas: uma delas é qual é a URL que queremos acessar, então que URL queremos permitir ou queremos manter segura na nossa aplicação.
- A outra coisa é basicamente as roles que queremos que estejam seguras. Para isso, para as URLs usamos a tag web-resource-collection e para a parte de roles, o outh-constraint.
- Vamos começar aqui podendo definir um nome só para que ele fique aparecendo bonito dentro do display
    - *<display-name>administration*
- O web-resource-name é um nome único onde você vai definir qual é o nome desse constraint de segurança, desse security-constraint. Com isso, nós temos a definição aqui de que esse web-resource-name, desse administration vai ser a parte de administração da nossa aplicação. O nome dele podemos utilizar como chaveamento, por isso que ele também não pode se repetir
- Além disso, temos que colocar também a URL pattern, qual é o nosso padrão de URL. Por exemplo: se queremos que ninguém acesse a parte de livros, foi o que acabamos de acessar, livros ou qualquer coisa depois de livros, então podemos usar um asterisco será protegido pelo JAAS. Esse é o nosso propósito, fazer com que as coisas fiquem protegidas pelo JAAS.

- Além disso, temos um o método HTTP que queremos e podemos utilizar. Por exemplo: o método HTTP GET e o método HTTP POST - <http-method>GET e <http-method>POST. Com isso, dizemos que dentro de /livros, nem GET, nem POST poderá ser executado a não ser que você tenha um determinado perfil. É isso que vamos informar agora.

- O role-name, que no nosso caso será admin>, <role-name>ADMIN. Temos aqui um role-name definindo que dentro de livros apenas os usuários que possuem a role ADMIN podem acessar ele. Isso é bem bacana, porque agora deixamos de fato a nossa aplicação segura.

- Lembrando que você tem que ser dado o nome, mas esse nome aqui ele é único, então eu não posso repetir esse nome de administration em outro local. Gostaria de ter a possibilidade de colocar mais roles aqui, temos que mapear um outro web-resource-collection dizendo qual vai ser a nossa próxima role. Podemos definir outras roles dentro de outh-constraint. Esse é o básico que tem em JMS.

- Recapitulando, security-constraint, podemos criar vários security-constranint e desse security-constranint nós definimos quais são as informações importantes de URL e role. Basicamente é isso que temos que definir para que o JAAS entenda como acessar, o que pode ser ou não acessado na nossa aplicação.

- Tivemos aqui um forbidden. Recebemos uma mensagem dizendo que não é permitido, não podemos acessar essa URL. Além disso, se percebermos aqui a "lista.html", teve o status "http 403", ou seja, nossa aplicação está segura, pelo menos essa parte, mas agora nenhum usuário consegue acessar isso. Nem nós que somos os administradores!

```
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    <!-- ... -->
    <security-constraint>
        <display-name>Administration</display-name>
        <web-resource-collection>
            <web-resource-name>administration</web-resource-name>
            <description>URLs que levam para a adminstração da loja</description>
            <url-pattern>/livros/*</url-pattern>
            <http-method>GET</http-method>
            <http-method>POST</http-method>
        </web-resource-collection>
        <auth-constraint>
            <role-name>ADMIN</role-name>
        </auth-constraint>
    </security-constraint>
</web-app>
```

### Criando a tela de login
- A página de login também é uma configuração aqui no web-xml. No web-xml nós vamos ter uma configuração chamada login-config. No login-config nós vamos informar qual é o método de autenticação.
- Por exemplo: eu quero autenticar via formulário. Existem algumas outras formas de autenticação, como HTTP basic, que é aquela telinha em cima para você digitar usuário, a senha e algumas outras formas.

- Nós vamos tratar do form a princípio. No form, nós queremos falar qual é o nosso formulário de fato de login. O nosso form de login é o form-login-page. No form-login-page nós não temos mais nenhum. Vamos colocar usuários/login.xhtml.

- Esse é o formulário que nós sabemos que é do login, no caso o JAAS sabe que esse é o formulário de login. Quando o usuário entrar e ele expirou a sessão, alguma coisa assim. Ele vai direcionar para a tela de login. Se o usuário tentar entrar diretamente na tela de login, nós também temos que modificar o local para onde ele vai.

- Na verdade, não é nem se ele entrar direto na tela de login, se ele entrar direto em uma outra página que precise de autenticação e ele não está autenticado. Por exemplo: mesma coisa usuarios/login.xhtml

- Para o login, temos um nome específico que precisamos enviar. O name dele tem que ser um específico, que o próprio JAAS entenda qual é o campo do login e qual é o campo da senha. Temos que fazer isso da forma que o JAAS entenda.

- O JAAS entende um nome chamado j_username, é o nome default que o JAAS vai entender e para a senha. O password, mesma coisa, então temos que colocar também o input, onde o nome desse input, o name dele vai j_password. Para que consigamos fazer com que esse campo seja realmente de senha, então colocamos o type = "password".

- Temos alguns detalhes que nós precisamos entender aqui. Primeiro, nós temos um formulário simples. Perceba que nós não estamos criando nada baseado em JSF, porque essa tela não precisa de nada especificamente baseado em JSF. Temos uma action, onde nós vamos precisar dizer de fato qual é a nossa action e a action é j_security_check.

- Essa é a URL padrão, a URL default que o JAAS entende para ser a nossa URL de login. Para que isso funcione e nós não percamos o contexto, vamos colocar aqui uma expression lenguage, onde colocamos o request.getContextPath. Desse modo, nós não perdemos o contexto da aplicação. Barra casa do código, barra e etc. O método de envio, a forma de envio obviamente será via POST.

- A galera gosta bastante de fazer um **@Deprecated**, vamos dizer assim, apenas para que os frameworks enxerguem essa classe. Eu não gosto muito dessa variável, desse tipo de coisa, mas às vezes é necessário, não tem muito o que fazer. Tem um construtor vazio só para os frameworks para que outros usuários não cheguem a usar isso.

### Configurando o JAAS no Wildfly
- pegar o usuário e a senha e validar no banco de dados.

- Nosso primeiro passo vai ser exatamente no banco de dados. Nós subimos a aplicação da outra vez e vimos que ele nos levou para tela de login, mas internamente o JPA, o hibernate criou as nossas tabelas, que era o que nós realmente queríamos.

- Temos a tabela SystemRole, SystemUser e SystemUser_SystemRole, que é para fazer um mapeamento ManyToMany. Para que esse mapeamento funcione e possamos ter um usuário de fato na nossa aplicação, nós devemos inserir aqui insert into SystemUser. Vamos começar pelo usuário onde as informações dos SystemUser vão ser o quê? Login e senha.

- É login mesmo que nós colocamos aqui. Deixe-me dar uma conferida só para nos lembrarmos no e-mail. E-mail, onde e-mail e senha tenham os valores, values, então vamos colocar os valores e-mail. Eu vou colocar o meu e-mail, `andre@caelum.com.br. Na senha vamos precisar colocar uma senha criptografada:insert into SystemUser (email, senha) values -> (andre@caelum.com.br, )`.

- Para uma senha criptografada nós temos que gerar essa senha, nós não temos uma senha criptografada. Vamos criar um gerador aqui de senha criptografada, uma nova classe e essa classe pode ser chamada de "PassGenerator". Então um gerador aqui de password, gerador de senhas, "login_security". Vamos colocar um pacotinho chamado Security.

- Dentro do nosso pacote security tem algumas coisas que nós queremos fazer. Começando, por exemplo, pela geração. Então vamos criar um método aqui generate public String generate, será ele que vai gerar, aqui nós precisamos receber a senha em texto e nós devemos retornar a senha em texto criptografado, a senha feito o hash: public String generate(String senhaTexto).

- Para que isso funcione, nós vamos pegar esse hash da classe message digest, que nós usávamos muito message digest para md5 como usamos para todas as outras coisas. Aqui digitamos MessageDigest.

- E daqui vamos pegar uma instância baseada no algoritmo. O algoritmo que nós vamos usar é o SHA256. O SHA256 é uma forma, um algoritmo de hash que ele faz com que a nossa senha fique mais segura baseada em 256 caracteres, se eu não tiver enganado.

- O MessageDigest vai nos retornar aqui uma distância disso, mas nós podemos ir direto pedir para que ele criptografe a senha no sentido de fato transformar a senha nesse hash, desde que nos passe a senha em texto para ele, mas ele quer os bytes, então: getBytes, MessageDigest.getInstance("sha-256").digest(senhaTexto.getbytes()). Dessa forma, estamos criptografando a senha e deixando a senha baseado em hash.

- Temos aqui o nosso digest! Deixe-me quebrar só para não ficar com barra de rolagem lateral, que geralmente não é legal - e não é mesmo. Agora que nós temos esses bytes, o que nós temos que fazer é transformar esses bytes de volta para texto, então vamos dar um return. Nesse caso, vamos usar um base64Encoder.encode(digest).

- Temos então a nossa excepction sendo gerada. Caso aconteça algum erro de transformação ou mesmo de encode, que dá uma um IOExcepction, esse Base64Encoder vai dar uma olhada aqui no pacote, org.jboss.security. Não é por acaso de fato, essa classe é uma classe que foi nos dada pelo JBoss, pelo pessoal do WildFly.

- Estamos usando um código proprietário, então temos que ter um certo cuidado. Nesse caso, estamos saindo um pouco do JAAS, no sentido de especificação e entrando um pouco a parte de segurança, que o próprio WildFly implementa. Infelizmente o JAAS não é completo nesse sentido.

- O JAAS tem vários GETs que não tornam ele uma especificação completa, uma especificação redonda, onde você consegue fazer tudo através da especificação. Isso é uma das grandes queixas da comunidade em relação ao JAAS, mas para uma segurança simples de aplicação, uma segurança mais baseada em usuários, senha e roles, que é aquilo que estamos fazendo é aceitável e o suficiente.

- Você pode usar um framework. Tem vários frameworks de autenticação que são baseados em Java EE e que aceitam essa comunicação com Java EE de forma bem natural e tranquila. Inclusive, via injeção de dependências, mas nós queremos que no Java EE 8 e posteriormente nós vamos ter uma integração ainda maior e o JAAS vai se consolidar cada vez mais.

- Vamos criar um main para que nós possamos utilizar nesse main uma forma de poder gerar senha que nós queremos, de forma fácil aqui. Por exemplo: senha 123, eu quero poder imprimir isso. Então vamos imprimir a senha aqui 123 de modo que possamos pegar esse valor.

- .System.out.println(new PassGenerator().generate("123")). Vou gerar no meu caso aqui com as teclas "Ctrl + F11", eu quero rodar um Java Application e conseguir pegar essa senha. Temos aqui uma senha. Acho que faltou um detalhe, deixe-me ver... Não, não faltou. Desculpe-me é base64, está aqui.

- Na verdade, quem dá o base64 é exatamente essa classe do WildFly. Eu queria que ele fosse base64 e que nós tivéssemos a informação mais segura. Coloquei a nossa senha, vou apertar a tecla "Enter". Nós temos um usuário: select * from SystemUuser;. "Paulo.alves@caelum.com.br" e nossa senha aqui.

- Vamos fazer um insert into SystemRole. No SystemRole nós vamos inserir o valor diretamente porque ele só tem um. Vou inserir o valor admin, para dizer que ele vai ser administrador. Nós ainda precisamos inserir os valores do SystemUser_SystemRole.

- Quero dar um desk para que nós possamos ver como que o hibernate criou, como que a JPA a nossa estrutura da tabela. Digitei insert intoSystemUser_SystemRole. Vamos colocar os valores diretamente, o nosso id ficou 1. Tudo bem, vocês podem ver que aqui no select que fizemos mais em cima, então está aqui id de 1 e o nosso o role-name ficou admin. Tecla "Enter"... Funcionou!

- Para que o JAAS entenda que nós de fato estamos trabalhando em cima uma da configuração de usuário e senha do JAAS, nós precisamos fazer configuração XML. Só que não web.xml, dessa vez no standalone-full.xml. Vou abrir aqui pelo Eclipse, nós vamos evitar de ficar navegando na estrutura de pacotes.

- security-domain é o subsystem que nós queremos? Não é esse subsystem que queremos. Vamos procurar o subsystem, "domain:security", aqui. Será se é esse aqui? Vamos ver, "security-domains". Beleza, temos dois "security-domain": um domain chamado de Other e um outro domain chamado de jboss-web-pollicy. É esse aqui mesmo.

- Vamos adicionar um novo security-domain aqui. Esse security-domain que já existe aqui em cima é um security-domain que já é utilizado pelo próprio Wildfly para controlar. Para ele controlar e não nós, para ele controlar toda parte de usuários. Por exemplo: o usuário que entra na administração, o usuário que acessa a JB externos, os usuários que entram na administração como usuário e como administrador.

- Tudo isso é baseado nessas roles que o JAAS entrega. No nosso caso, vamos ter que criar mais um security-domain. Nesse security-domain nós vamos colocar um authentication, que é nossa autenticação. Perceba que aqui temos o authentication JASP, mais algumas outras coisas, autorizetion e tudo mais. Nós vamos usar esse authentication.

- Dentro do nosso authentication, nós vamos colocar algumas informações, mas antes aqui no security-domain nós precisamos de um nome. Esse nome vai ser o nome que nós vamos ligar com a nossa aplicação. Por exemplo: database-login. Baseado na nossa aplicação, nós vamos querer um domínio de autenticação via login.

- Além do nome, nós podemos configurar um cache-type apenas para que o próprio Wildfly saiba que ele vai usar o cache-type default mesmo. Perceba que é o que é a grande maioria das aplicações aqui está utilizando.

- Temos a nossa tag de autenticação, vamos seguir o fluxo onde nós vamos criar um login-module. Dentro desse login-module, vamos colocar a forma que nós vamos fazer o login, ainda que dentro dos parâmetros. Por exemplo: qual vai ser o code dele?

- Vai ser o Database e a flag dele vai ser required: <login-modulo code="Database" flag="require">. Esse code serve para informar qual é a forma de autenticação que nós vamos utilizar. No nosso caso, vamos usar forma de autenticação via banco de dados.

- A flag é para dizermos que nem um usuário da aplicação vai conseguir logar, a não ser que ele passe por essa autenticação de banco de dados. Tem algumas outras formas de fazermos autenticação além do banco de dados, LDAP, por exemplo, HTTP Basic e várias outras que podemos encontrar facilmente nesse link das formas de fazermos autenticação do Wildfly, do JAAS via pesquisa fácil no Google.

- Mas eu posso pegar também um link para você daqui a pouco, apenas para que nós não saíamos daqui agora. Vamos continuar a nossa configuração do login module. Além dessa configuração no login-module dizendo que isso é required, nós temos que dizer qual é a forma de login.

- Temos aqui um module-option. Não se preocupe galera, eu não decorei isso aqui. É impossível nós ficarmos lembrando e decorando tudo isso! Temos realmente que ficar olhando porque não tem jeito, é uma coisa que é quase impossível nós decorarmos.

- O module-option nós usamos aqui como o nome, por exemplo, como qualquer coisa. O nome pode ser qualquer um, mas eu quero colocar dsJndiname, já deu para entender. O valor dele, o value, é exatamente o nosso java:jboss/datasources/casadocodigoDS.

- Se nós pegarmos essa informação e dermos uma busca, nós vamos encontrar o nosso Datasource que nós já configuramos. Essa é uma forma de fazer com que o próprio JAAS entenda que nós temos essa configuração já na nossa aplicação via JNDI. Isso é exatamente o JDNIName que nós temos.

- Vamos voltar então para lá, aqui. dsJndiName é exatamente o nome do módulo que nós queremos carregar para que ele entenda que de fato nós estamos buscando pelo nome JNDI. Não estamos buscando de qualquer lugar, estamos buscando de um lugar específico, JDNIName. Então com isso nós carregamos o Datasource.

- Uma vez descarregado o Datasource, nós temos que dizer quais são as queries, quais são as consultas que vão fazer sentido para que nós possamos buscar os usuários, buscar as roles. Nesse passo, nós vamos colocar mais um module-option name, onde o nome desse cara, o nome desse module que nós vamos usar é principalQuery.

- Nesse **principalsQuery** nós vamos trabalhar nesse caso com cuidado porque o valor dele é exatamente a Query que vai buscar o usuário. O principal é uma forma que praticamente todos os frameworks de segurança usam para chamar o usuário autenticado, então ele é o principal. O usuário autentificado ele acaba se tornando o principal do sistema, é por isso que eles acabam chamando de principal.

- Essa Query vai ser uma query bem simples. Nesse caso do principal é uma Query bem simples. Por exemplo: se nós quisermos buscar os dados aqui de quem vai fazer o login do sistema, é só fazermos um select * form SystemUser where email = paulo.alves@caelum.com.br. Vamos lá! Nós conseguimos trazer, mas tem um detalhe: será que realmente o System principalQuery, o SystemUser nós temos que pegar todas as informações?

- Se nós estamos querendo fazer login e o e-mail eu já passei para ele, então ele não quer o e-mail, ele quer apenas a senha. Exatamente! Nesse caso, nós vamos ter que trocar aqui o select para trazermos apenas a senha, então: select senha from SystemUser where e-mail =.

- Não posso colocar e-mail diretamente lá, senão nós já sabemos que nós estamos buscando o e-mail de fato. No JDBC, que é a forma como ele faz essa busca? Toda vez que nós vamos passar um parâmetro, nós vamos colocar uma interrogação. Então vamos colocar a interrogação para que ele possa buscar o nosso usuário pelo e-mail.

- Vamos continuando aqui, temos mais um name aqui, que vai ser o rolesQuery e o value dele vai ser um pouco maior do que o principalQuery. Nesse caso, o que nós temos que fazer é buscar select.

- Vamos colocar um asterisco por enquanto, buscar todas as roles que o nosso principal tem. O que nós encontramos tem, então vamos fazer aqui uma busca antes de mais nada, vamos fazer aquele desk de novo.

- Está aí a descrição para nós sabermos o que queremos buscar. O que queremos é pegar daquele usuário quais são as roles, que ele tem permissão. Para nós fazermos isso, só temos que buscar nessa tabela, então vamos fazer aqui um select * from SystemUser_SystemRole.

- Eu vou chamar de sysRole. Nós vamos fazer um inner joinSystemUsers u. Na verdade, temos que fazer a junção, onde sysRole.SystemUser_id = su.id, where su.email = andre@caelum.com.br.

- Vamos ver o que nós conseguimos com isso! Nós trouxemos tudo, trouxemos o ID, a role, o ID de novo, que agora cada uma é de uma tabela diferente, e-mail e a senha. Feito isso, nós já sabemos que temos que trazer.

- Mas da mesma forma que antes, nós não queremos todas essas informações, nós queremos apenas o roles_name. Vamos pegar aqui roles_name. Nesse caso, galera, infelizmente nem dá para dizermos muita coisa, só precisamos realmente de mais um aqui chamado roles.

- Por quê? Nós fazemos de novo, nós precisamos desse cara chamado roles, vamos colocar aqui de novo então o roles_name. Esses roles_name vêm do sysrole.role_name e nós precisamos de um roles aqui. Olhe só o nosso retorno, é um retorno padrão que o próprio JAAS exige.

- Como nossa forma de autenticação é de uma única aplicação, então temos que retornar as roles que os usuários têm, porque nós poderíamos trabalhar através de grupos e com grupos nós teríamos em um grupo várias roles e teríamos que trazer os grupos. Mas como estamos usando uma forma simples de autenticação, a nossa aplicação não precisa de algo diferente disso, nós podemos usar simplesmente a role ali diretamente. Para isso, nós temos que trazer além do select simples, um outro campo chamado de roles por default.

- Vamos copiar esse select, exceto a parte do login aqui mais uma vez, do e-mail. Voltando aqui na nossa aplicação, nós colocamos esse valor aqui, mas claro que para não ficar também tão gigante, uma barra de rolagem gigante nós quebramos esse select aqui em algumas partes. Para fecharmos mais uma vez a interrogação aqui.

- Essa é a nossa forma de buscar todas as roles da aplicação. Temos mais um module-option. Nesse module-option o nome que nós vamos configurar será o hashAlgothm. O hashAlgothm é exatamente o algoritmo que nós usamos aqui para hash, então o algoritmo que nós usamos aqui em hash o value é o SHA-256.

- Além dessa parte, nós também precisamos do hashEncoding. O Enconding é base-64, que é exatamente a forma de encodar, de fazer codificação em nosso algoritmo. Salvamos agora aqui a nossa configuração. É uma configuração que não é longa, mas também não é tão simples.

- O grande passo aqui é que cada module-option pelo nome vai definir uma propriedade que você tem que fazer. Essas module-option vêm de acordo com o seu code do loginmodule do JAAS aqui. Vamos salvar essa configuração. Se você quiser, pode procurar aqui na web por security subsystem configuration wildfly.

- Vamos ver aqui o que nós encontramos. Aqui esse primeiro link. Nesse primeiro link acho que nós vamos conseguir ter. Então fica aí a URL para você poder saber quais são as propriedades que nós temos.

- Temos aqui o security manager, temos várias propriedades aqui, security domains, todos os codes aqui, o cliente, certificate, database, que é o que estamos utilizando,* database* aqui, database certificate... Então temos várias formas de fazermos essa configuração. Fica o link que eu tinha falado antes para que você possa utilizar caso você deseje.

- Estamos chegando no final e ainda não acabou! Acalme-se! É só mais um passo simples aqui! Nós temos que vir aqui dentro do nosso jboss_web. Nós não criamos ainda, mas precisamos criar um arquivo chamado jboss_web. Dentro desse arquivo nós colocamos a configuração necessária para que consigamos fazer o login. A configuração necessária é ligar esse domain, database de login com a nossa aplicação.

- Vamos fazer isso! Aqui dentro da pasta web-inf vamos criar um novo XML, novo arquivo XML file, que nós vamos chamar de jboss-web.xml. No jboss-web.xml deixamos essas configurações de UTF. Nós vamos fazer uma configuração de jboss-web, onde dentro nós precisamos colocar o <security-domain>database-login</security-domain>.

- Imaginamos que temos todas as configurações necessárias, vamos subir o servidor ou reiniciar o servidor e ver se a nossa configuração rodou ou não. Servidor subiu, vamos lá! Vou apertar a tecla "Enter" aqui para ler e carregar de novo. Vou colocar o meu e-mail, senha, login e aparentemente algum problema aconteceu, espere!

- Aqui é principal, então eu errei aqui, faltou um s na descrição e na hora de digitar. Vamos reiniciar de novo! O servidor subiu, vamos mais uma vez aqui, "livros/listas". Vamos entrar aqui de novo, 123. Vai demorar um pouco. Logou! Com o nosso login, agora nós conseguimos saber se o usuário tem ou não permissão de fato para entrar aqui na parte de cadastro!

### Configurando o Widlfly
- Abra o standalone-full.xml no Eclipse:
- Faça uma busca pela tag security-domains, que está associada ao namespace urn:jboss:domain:security:1.2:
```
<security-domains>
    <!-- ... -->
    <security-domain name="database-login" cache-type="default">
        <authentication>
            <login-module code="Database" flag="required">
                <module-option name="dsJndiName"
                    value="java:jboss/datasources/casadocodigoDS" />

                <module-option name="principalsQuery"
                    value="select senha from SystemUser where email = ?" />

                <module-option name="rolesQuery"
                    value="select sysRole.roles_name,'Roles'
                        from SystemUser_SystemRole sysRole inner join SystemUser
                        su on sysRole.SystemUser_id = su.id where su.email = ?" />

                <module-option name="hashAlgorithm" value="SHA-256" />

                <module-option name="hashEncoding" value="base64" />
            </login-module>
        </authentication>
    </security-domain>
</security-domains>
```

- Dentro do diretório WEB-INF, crie um novo arquivo chamado jboss-web.xml com a configuração necessária para que possamos fazer o login. Precisamos ligar o login com a nossa aplicação:
```
<?xml version="1.0" encoding="UTF-8"?>
<jboss-web>
    <security-domain>database-login</security-domain>
</jboss-web>
```

- Reinicie o servidor e tente acessar a lista de livros (http://localhost:8080/casadocodigo/livros/lista.xhtml) e em seguida se logar na aplicação com o e-mail e senha(sem criptografia) que você cadastrou.

### Exibindo demais dados do usuário
- A forma mais fácil de todas é utilizarmos o c:if. O c:if já é conhecido, você usou o JSTL. O JSTL pode ser utilizado em alguns momentos. Por exemplo: quando nós queremos pegar alguma informação, verificar alguma informação desde que numa determinada tela ele não influencie o ciclo de vida do JSF.

- Agora precisamos verificar se o usuário tem acesso a administração, pois caso não, não vamos exibir o link. Vamos fazer isso utilizando a taglib c:if:

```
<c:if test="#{currentUser.hasRole('ADMIN')}">
    <li class="colecoesDaCDC-colecaoItem">
        <a href="#{request.contextPath}/livros/lista.xhtml" class="colecoesDaCDC-colecaoLink">
            Administração
        </a>
    </li>
</c:if>
```

#### Usuário logado
```

@Model
public class CurrentUser {

	@Inject
	private HttpServletRequest request;

	@Inject
	private SecurityDao securityDao;

	private SystemUser systemUser;

	public SystemUser get() {
		return systemUser;
	}
	
	public boolean hasRole(String role) {
		return request.isUserInRole(role);
	}

	@PostConstruct
	private void loadSystemUser() {
		Principal principal = request.getUserPrincipal();
		if (principal != null) {
			String email = request.getUserPrincipal().getName();
			systemUser = securityDao.findByEmail(email);
		}
	}

}
```

<a name="anc5"></a>

## Utilizando template

<a name="anc6"></a>

## Utilize WebSockets para comunicação Síncrona
