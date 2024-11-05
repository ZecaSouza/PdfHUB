# PdfHUB

PDFHub
O PDFHub é uma rede social de compartilhamento de PDFs livres, onde os usuários podem fazer upload de documentos e compartilhar com outras pessoas. É possível marcar PDFs como públicos, privados, disponíveis para download ou apenas leitura. Além disso, estamos criando um sistema onde cada usuário poderá seguir outros, curtir PDFs e acessar um feed personalizado com base nos interesses e relevância dos documentos.

Sobre o Projeto
Estamos nos estágios iniciais do desenvolvimento, construindo o primeiro MVP (Produto Mínimo Viável). Este MVP está sendo implementado com Kotlin, Spring e PostgreSQL para o armazenamento de dados. Por enquanto, a aplicação é simples, mas planejamos adicionar funcionalidades futuras, como:

Criação e gerenciamento de contas de usuário
Sistema de seguidores para acompanhar os uploads de outros usuários
Definição de roles para garantir a segurança e a organização do sistema
Configuração do Banco de Dados
Para o banco de dados, utilizamos o PostgreSQL em um container Docker. Basta adicionar o Docker PostgreSQL, e, caso seja necessário ajustar alguma configuração, você pode modificar diretamente o arquivo application.properties da aplicação.
