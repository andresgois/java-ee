# Java EE parte 3: Finalizando sua loja com REST, JMS, JAAS e WebSockets


### Links
- [Responda à requisições nos formatos XML e JSON](#anc1)
- [Envie e-mails para o usuário](#anc2)
- [Trabalhe Assincronamente com JMS](#anc3)
- [Proteja sua aplicação com JAAS](#anc4)
- [Utilize WebSockets para comunicação Síncrona](#anc5)

- [Site](http://localhost:8080/casadocodigo/index.xhtml)
##

<a name="anc1"></a>

## Responda à requisições nos formatos XML e JSON 
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
- [Elemento Raiz](../../../asserts/005-collection-raiz.png)
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

### JMS

- Dentro do JMS temos dois tipos de mensagens assíncronas que podemos enviar:

    - Tópico (topic): lista de discussão, como grupo de e-mail ou whatsapp, onde todos recebem a mesma mensagem.
    - Fila (queue): envia um e-mail para o primeiro da fila, o segundo poderá receber outra mensagem, diferente do Tópico.
- O /jms abre o contexto JMS no Wildfly, mas o /topics é aleatório, podendo ser substituído por, por exemplo, /topic ou /topico.
- Temos então o Destination sendo injetado e o envio da mensagem pelo producer.send(destination, compra.getUuid()). O próximo passo configuraremos quem irá ouvir (listener) a mensagem.

<a name="anc3"></a>

## Trabalhe Assincronamente com JMS

<a name="anc4"></a>

## Proteja sua aplicação com JAAS

<a name="anc5"></a>

## Utilize WebSockets para comunicação Síncrona
