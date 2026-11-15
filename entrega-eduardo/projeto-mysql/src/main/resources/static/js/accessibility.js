(() => {
  'use strict';

  const body = document.body;
  const root = document.documentElement;
  const languageSelect = document.getElementById('languageSelect');

  // Textos da interface. Dados cadastrados pelos usuários não são traduzidos.
  const translations = {
    pt: {
      brandLibrary: 'Biblioteca', brandCollection: 'Acervo',
      navHome: 'Início', navBooks: 'Pesquisar livros', navNewBook: 'Cadastrar livro',
      navAuthors: 'Autores', navPublishers: 'Editoras', navCopies: 'Exemplares',
      navLoans: 'Empréstimos', navHistory: 'Histórico', navUsers: 'Usuários',
      navLogin: 'Entrar', navRegister: 'Cadastrar-se', navLogout: 'Sair',
      mainNavLabel: 'Navegação principal', accessibilityLabel: 'Opções de acessibilidade',
      accessTitle: 'Acessibilidade', languageLabel: 'Idioma',
      languagePt: 'Português', languageEn: 'English', languageEs: 'Español',
      fontDecreaseLabel: 'Diminuir fonte', fontIncreaseLabel: 'Aumentar fonte',
      darkMode: 'Modo escuro', contrast: 'Alto contraste', readPage: 'Ler página',
      footerText: 'Sistema Web de Gerenciamento de Biblioteca e Histórico do Acervo · Protótipo acadêmico',
      speakUnsupported: 'Seu navegador não oferece conversão de texto em voz.',

      loginPageTitle: 'Entrar', loginEyebrow: 'ACESSO AO SISTEMA', loginHeading: 'Entrar',
      loginError: 'Identificador ou senha inválidos.', loginEmailLabel: 'E-mail ou CPF',
      loginEmailPlaceholder: 'Seu e-mail ou CPF', loginPasswordLabel: 'Senha',
      loginSubmit: 'Entrar', loginNoAccount: 'Ainda não tem cadastro?',
      loginRegisterLink: 'Cadastre-se',

      registrations: 'CADASTROS', authorFormPageTitle: 'Cadastro de autor',
      authorNewTitle: 'Novo autor', authorEditTitle: 'Editar autor',
      authorName: 'Nome', authorBirthDate: 'Data de nascimento', save: 'Salvar',
      cancel: 'Cancelar', authorsPageTitle: 'Autores', authorsHeading: 'Autores',
      authorAdd: '+ Novo autor', nameColumn: 'Nome', birthDateColumn: 'Data de nascimento',
      actionsColumn: 'Ações', edit: 'Editar', delete: 'Excluir',
      authorDeleteConfirm: 'Excluir este autor?', authorEmpty: 'Nenhum autor cadastrado.',

      publisherFormPageTitle: 'Cadastro de editora', publisherNewTitle: 'Nova editora',
      publisherEditTitle: 'Editar editora', publisherCompanyName: 'Razão social',
      cnpjLabel: 'CNPJ (14 números)', addressLabel: 'Endereço', cityLabel: 'Cidade',
      publishersPageTitle: 'Editoras', publishersHeading: 'Editoras',
      publisherAdd: '+ Nova editora', cnpjColumn: 'CNPJ', addressColumn: 'Endereço',
      cityColumn: 'Cidade', publisherDeleteConfirm: 'Excluir esta editora?',
      publisherEmpty: 'Nenhuma editora cadastrada.',

      loanFormPageTitle: 'Registrar empréstimo', circulation: 'CIRCULAÇÃO',
      loanRegisterHeading: 'Registrar empréstimo', userLabel: 'Usuário',
      selectUser: 'Selecione o usuário', availableCopyLabel: 'Exemplar disponível',
      selectCopy: 'Selecione o exemplar', loanPeriod: 'Prazo (dias)',
      confirmLoan: 'Confirmar empréstimo', loanNote: 'O sistema calcula a data prevista de devolução a partir da data atual e do prazo informado. O empréstimo altera o estado do exemplar para EMPRESTADO.',
      loansPageTitle: 'Empréstimos', loansHeading: 'Empréstimos e devoluções',
      loansDescription: 'Registre retiradas, prazos, devoluções e condições físicas.',
      newLoan: '+ Novo empréstimo', bookCopyColumn: 'Livro / exemplar',
      userColumn: 'Usuário', withdrawalColumn: 'Retirada', deadlineColumn: 'Prazo',
      returnDateColumn: 'Devolução', situationColumn: 'Situação',
      registerReturnColumn: 'Registrar devolução', pending: 'Pendente',
      loanLate: 'Atrasado', loanOpen: 'Em aberto', loanReturned: 'Devolvido',
      damage: 'Avaria', damagePlaceholder: 'Observação da avaria',
      returnButton: 'Devolver', loanEmpty: 'Nenhum empréstimo registrado.',

      copiesPageTitle: 'Exemplares', physicalCollection: 'ACERVO FÍSICO',
      copiesHeading: 'Exemplares', copiesDescription: 'Unidades físicas cadastradas para os livros.',
      registerCopies: 'Cadastrar exemplares', codeColumn: 'Código', bookColumn: 'Livro',
      stateColumn: 'Estado', observationsColumn: 'Observações',
      maintenance: 'Manutenção', discard: 'Descartar', discardConfirm: 'Confirmar descarte?',
      copiesEmpty: 'Nenhum exemplar cadastrado.',

      historyPageTitle: 'Histórico do acervo', collectionAudit: 'AUDITORIA DO ACERVO',
      historyHeading: 'Histórico de movimentações',
      historyDescription: 'Entradas, cadastros, empréstimos, devoluções, manutenções e descartes.',
      movementType: 'Tipo de movimentação', all: 'Todas', filter: 'Filtrar',
      clear: 'Limpar', dateTimeColumn: 'Data e hora', typeColumn: 'Tipo',
      descriptionColumn: 'Descrição', responsibleColumn: 'Responsável',
      historyEmpty: 'Nenhuma movimentação registrada.',

      bookDetailPageTitle: 'Detalhe do livro', backToSearch: '← Voltar à pesquisa',
      collectionDetails: 'DETALHES DO ACERVO', volumeLabel: 'Volume', isbnLabel: 'ISBN',
      publisherLabel: 'Editora', authorsLabel: 'Autor(es)', registeredCopies: 'Exemplares cadastrados',
      availableCopies: 'Disponíveis', editRegistration: 'Editar cadastro',
      bookFormPageTitle: 'Cadastro de livro', collectionManagement: 'GERENCIAMENTO DO ACERVO',
      addBookTitle: 'Cadastrar livro', editBookTitle: 'Editar livro', titleLabel: 'Título',
      volumeNumber: 'Número do volume', isbnPlaceholder: 'Ex.: 978-...',
      selectPublisher: 'Selecione uma editora',
      publisherBeforeBook: 'Cadastre a editora antes de cadastrar o livro.',
      selectAuthors: 'Autores (selecione um ou mais)',
      multipleAuthorsHint: 'Para selecionar vários, segure Ctrl enquanto clica.',
      totalCopies: 'Quantidade total de exemplares desejada',
      copiesEditHint: 'Ao editar, o sistema adiciona exemplares até atingir essa quantidade. Ele não apaga exemplares existentes.',
      saveBook: 'Salvar livro', recommendedOrder: 'Ordem recomendada:',
      recommendedOrderText: 'cadastre primeiro autores e editoras; depois cadastre o livro e informe quantos exemplares físicos existem.',
      booksPageTitle: 'Pesquisar livros', collection: 'ACERVO',
      booksHeading: 'Pesquisar livros', searchInstructions: 'Use um ou mais campos para filtrar os resultados.',
      addBook: '+ Cadastrar livro', titleFilter: 'Título', authorFilter: 'Autor',
      publisherFilter: 'Editora', isbnFilter: 'ISBN', bookTitlePlaceholder: 'Título do livro',
      authorNamePlaceholder: 'Nome do autor', publisherNamePlaceholder: 'Nome da editora',
      isbnCodePlaceholder: 'Código ISBN', search: 'Buscar', bookColumn: 'Livro',
      authorsColumn: 'Autor(es)', publisherColumn: 'Editora', actionColumn: 'Ação',
      details: 'Detalhes', noBooksFound: 'Nenhum livro encontrado. Ajuste os filtros ou cadastre um livro.',

      usersAdminPageTitle: 'Gerenciar usuários', administration: 'ADMINISTRAÇÃO',
      usersHeading: 'Usuários', usersDescription: 'Consulte os usuários e cadastre funcionários.',
      registerEmployeeHeading: 'Cadastrar funcionário', emailLabel: 'E-mail',
      cpfLabel: 'CPF (11 números)', cepLabel: 'CEP (8 números)',
      passwordMinimum: 'Senha (mínimo 8 caracteres)', registerEmployee: 'Cadastrar funcionário',
      registeredUsersHeading: 'Usuários cadastrados', emailColumn: 'E-mail',
      cpfColumn: 'CPF', cepColumn: 'CEP', userTypeColumn: 'Tipo',

      userRegisterPageTitle: 'Cadastro de usuário', newRegistration: 'NOVO CADASTRO',
      createUserHeading: 'Criar conta de usuário',
      publicRegistrationDescription: 'O cadastro feito por esta página cria um usuário comum.',
      fullName: 'Nome completo', register: 'Cadastrar',

      errorPageTitle: 'Erro', errorHeading: 'Não foi possível concluir a operação',
      errorDescription: 'Verifique os dados e tente novamente. Se o problema continuar, retorne à página inicial.',
      backToHome: 'Voltar ao início',

      homePageTitle: 'Biblioteca Acervo', homeEyebrow: 'SISTEMA DE GERENCIAMENTO DE BIBLIOTECA',
      homeHeading: 'Encontre sua próxima leitura.',
      homeDescription: 'Consulte o acervo, verifique informações dos livros e acompanhe a disponibilidade dos exemplares.',
      searchCollection: 'Pesquisar no acervo', collectionCard: 'Acervo',
      collectionCardDescription: 'Cadastro e organização de livros, autores, editoras e exemplares.',
      consultBooks: 'Consultar livros →', researchCard: 'Pesquisa',
      researchCardDescription: 'Pesquise por título, autor, editora ou ISBN.',
      openSearch: 'Abrir pesquisa →', loansCard: 'Empréstimos',
      loansCardDescription: 'Registre retiradas, devoluções, atrasos e avarias.',
      manageLoans: 'Gerenciar empréstimos →', historyCard: 'Histórico',
      historyCardDescription: 'Consulte as movimentações realizadas no acervo.',
      viewMovements: 'Ver movimentações →', registrationCard: 'Cadastro',
      registrationCardDescription: 'Crie seu cadastro para utilizar os serviços da biblioteca.',
      registerUser: 'Cadastrar usuário →', academicPrototype: 'Protótipo acadêmico:',
      prototypeDescription: 'dados armazenados localmente em banco relacional H2. O acesso administrativo deve ser feito por funcionário.'
    },

    en: {
      brandLibrary: 'Library', brandCollection: 'Collection',
      navHome: 'Home', navBooks: 'Search books', navNewBook: 'Register book',
      navAuthors: 'Authors', navPublishers: 'Publishers', navCopies: 'Copies',
      navLoans: 'Loans', navHistory: 'History', navUsers: 'Users',
      navLogin: 'Sign in', navRegister: 'Sign up', navLogout: 'Log out',
      mainNavLabel: 'Main navigation', accessibilityLabel: 'Accessibility options',
      accessTitle: 'Accessibility', languageLabel: 'Language',
      languagePt: 'Português', languageEn: 'English', languageEs: 'Español',
      fontDecreaseLabel: 'Decrease font size', fontIncreaseLabel: 'Increase font size',
      darkMode: 'Dark mode', contrast: 'High contrast', readPage: 'Read page',
      footerText: 'Library Management and Collection History Web System · Academic prototype',
      speakUnsupported: 'Your browser does not support text-to-speech.',

      loginPageTitle: 'Sign in', loginEyebrow: 'SYSTEM ACCESS', loginHeading: 'Sign in',
      loginError: 'Invalid identifier or password.', loginEmailLabel: 'Email or CPF',
      loginEmailPlaceholder: 'Your email or CPF', loginPasswordLabel: 'Password',
      loginSubmit: 'Sign in', loginNoAccount: 'Don’t have an account?',
      loginRegisterLink: 'Sign up',

      registrations: 'REGISTRATION', authorFormPageTitle: 'Author registration',
      authorNewTitle: 'New author', authorEditTitle: 'Edit author',
      authorName: 'Name', authorBirthDate: 'Date of birth', save: 'Save',
      cancel: 'Cancel', authorsPageTitle: 'Authors', authorsHeading: 'Authors',
      authorAdd: '+ New author', nameColumn: 'Name', birthDateColumn: 'Date of birth',
      actionsColumn: 'Actions', edit: 'Edit', delete: 'Delete',
      authorDeleteConfirm: 'Delete this author?', authorEmpty: 'No authors registered.',

      publisherFormPageTitle: 'Publisher registration', publisherNewTitle: 'New publisher',
      publisherEditTitle: 'Edit publisher', publisherCompanyName: 'Company name',
      cnpjLabel: 'CNPJ (14 digits)', addressLabel: 'Address', cityLabel: 'City',
      publishersPageTitle: 'Publishers', publishersHeading: 'Publishers',
      publisherAdd: '+ New publisher', cnpjColumn: 'CNPJ', addressColumn: 'Address',
      cityColumn: 'City', publisherDeleteConfirm: 'Delete this publisher?',
      publisherEmpty: 'No publishers registered.',

      loanFormPageTitle: 'Register loan', circulation: 'CIRCULATION',
      loanRegisterHeading: 'Register loan', userLabel: 'User',
      selectUser: 'Select a user', availableCopyLabel: 'Available copy',
      selectCopy: 'Select a copy', loanPeriod: 'Loan period (days)',
      confirmLoan: 'Confirm loan', loanNote: 'The system calculates the expected return date using the current date and the selected loan period. The loan changes the copy status to BORROWED.',
      loansPageTitle: 'Loans', loansHeading: 'Loans and returns',
      loansDescription: 'Record checkouts, due dates, returns, and physical condition.',
      newLoan: '+ New loan', bookCopyColumn: 'Book / copy',
      userColumn: 'User', withdrawalColumn: 'Checkout', deadlineColumn: 'Due date',
      returnDateColumn: 'Return date', situationColumn: 'Status',
      registerReturnColumn: 'Register return', pending: 'Pending',
      loanLate: 'Overdue', loanOpen: 'Open', loanReturned: 'Returned',
      damage: 'Damage', damagePlaceholder: 'Damage notes',
      returnButton: 'Return', loanEmpty: 'No loans registered.',

      copiesPageTitle: 'Copies', physicalCollection: 'PHYSICAL COLLECTION',
      copiesHeading: 'Copies', copiesDescription: 'Physical units registered for the books.',
      registerCopies: 'Register copies', codeColumn: 'Code', bookColumn: 'Book',
      stateColumn: 'Status', observationsColumn: 'Notes',
      maintenance: 'Maintenance', discard: 'Discard', discardConfirm: 'Confirm discard?',
      copiesEmpty: 'No copies registered.',

      historyPageTitle: 'Collection history', collectionAudit: 'COLLECTION AUDIT',
      historyHeading: 'Movement history',
      historyDescription: 'Entries, registrations, loans, returns, maintenance, and discards.',
      movementType: 'Movement type', all: 'All', filter: 'Filter',
      clear: 'Clear', dateTimeColumn: 'Date and time', typeColumn: 'Type',
      descriptionColumn: 'Description', responsibleColumn: 'Responsible person',
      historyEmpty: 'No movements registered.',

      bookDetailPageTitle: 'Book details', backToSearch: '← Back to search',
      collectionDetails: 'COLLECTION DETAILS', volumeLabel: 'Volume', isbnLabel: 'ISBN',
      publisherLabel: 'Publisher', authorsLabel: 'Author(s)', registeredCopies: 'Registered copies',
      availableCopies: 'Available', editRegistration: 'Edit record',
      bookFormPageTitle: 'Book registration', collectionManagement: 'COLLECTION MANAGEMENT',
      addBookTitle: 'Register book', editBookTitle: 'Edit book', titleLabel: 'Title',
      volumeNumber: 'Volume number', isbnPlaceholder: 'E.g.: 978-...',
      selectPublisher: 'Select a publisher',
      publisherBeforeBook: 'Register the publisher before registering the book.',
      selectAuthors: 'Authors (select one or more)',
      multipleAuthorsHint: 'To select multiple authors, hold Ctrl while clicking.',
      totalCopies: 'Desired total number of copies',
      copiesEditHint: 'When editing, the system adds copies until this quantity is reached. It does not delete existing copies.',
      saveBook: 'Save book', recommendedOrder: 'Recommended order:',
      recommendedOrderText: 'register authors and publishers first; then register the book and enter how many physical copies exist.',
      booksPageTitle: 'Search books', collection: 'COLLECTION',
      booksHeading: 'Search books', searchInstructions: 'Use one or more fields to filter the results.',
      addBook: '+ Register book', titleFilter: 'Title', authorFilter: 'Author',
      publisherFilter: 'Publisher', isbnFilter: 'ISBN', bookTitlePlaceholder: 'Book title',
      authorNamePlaceholder: 'Author name', publisherNamePlaceholder: 'Publisher name',
      isbnCodePlaceholder: 'ISBN code', search: 'Search', bookColumn: 'Book',
      authorsColumn: 'Author(s)', publisherColumn: 'Publisher', actionColumn: 'Action',
      details: 'Details', noBooksFound: 'No books found. Adjust the filters or register a book.',

      usersAdminPageTitle: 'Manage users', administration: 'ADMINISTRATION',
      usersHeading: 'Users', usersDescription: 'View users and register staff members.',
      registerEmployeeHeading: 'Register staff member', emailLabel: 'Email',
      cpfLabel: 'CPF (11 digits)', cepLabel: 'ZIP code (8 digits)',
      passwordMinimum: 'Password (at least 8 characters)', registerEmployee: 'Register staff member',
      registeredUsersHeading: 'Registered users', emailColumn: 'Email',
      cpfColumn: 'CPF', cepColumn: 'ZIP code', userTypeColumn: 'Type',

      userRegisterPageTitle: 'User registration', newRegistration: 'NEW REGISTRATION',
      createUserHeading: 'Create user account',
      publicRegistrationDescription: 'Registration on this page creates a regular user account.',
      fullName: 'Full name', register: 'Register',

      errorPageTitle: 'Error', errorHeading: 'The operation could not be completed',
      errorDescription: 'Check the information and try again. If the problem persists, return to the home page.',
      backToHome: 'Back to home',

      homePageTitle: 'Library Collection', homeEyebrow: 'LIBRARY MANAGEMENT SYSTEM',
      homeHeading: 'Find your next read.',
      homeDescription: 'Browse the collection, check book information, and see copy availability.',
      searchCollection: 'Search the collection', collectionCard: 'Collection',
      collectionCardDescription: 'Registration and organization of books, authors, publishers, and copies.',
      consultBooks: 'Browse books →', researchCard: 'Search',
      researchCardDescription: 'Search by title, author, publisher, or ISBN.',
      openSearch: 'Open search →', loansCard: 'Loans',
      loansCardDescription: 'Record checkouts, returns, overdue loans, and damage.',
      manageLoans: 'Manage loans →', historyCard: 'History',
      historyCardDescription: 'View movements recorded in the collection.',
      viewMovements: 'View movements →', registrationCard: 'Registration',
      registrationCardDescription: 'Create an account to use the library services.',
      registerUser: 'Register user →', academicPrototype: 'Academic prototype:',
      prototypeDescription: 'data is stored locally in an H2 relational database. Administrative access must be performed by a staff member.'
    },

    es: {
      brandLibrary: 'Biblioteca', brandCollection: 'Colección',
      navHome: 'Inicio', navBooks: 'Buscar libros', navNewBook: 'Registrar libro',
      navAuthors: 'Autores', navPublishers: 'Editoriales', navCopies: 'Ejemplares',
      navLoans: 'Préstamos', navHistory: 'Historial', navUsers: 'Usuarios',
      navLogin: 'Entrar', navRegister: 'Registrarse', navLogout: 'Salir',
      mainNavLabel: 'Navegación principal', accessibilityLabel: 'Opciones de accesibilidad',
      accessTitle: 'Accesibilidad', languageLabel: 'Idioma',
      languagePt: 'Português', languageEn: 'English', languageEs: 'Español',
      fontDecreaseLabel: 'Disminuir tamaño de fuente', fontIncreaseLabel: 'Aumentar tamaño de fuente',
      darkMode: 'Modo oscuro', contrast: 'Alto contraste', readPage: 'Leer página',
      footerText: 'Sistema web de gestión de biblioteca e historial de la colección · Prototipo académico',
      speakUnsupported: 'Su navegador no admite la conversión de texto a voz.',

      loginPageTitle: 'Entrar', loginEyebrow: 'ACCESO AL SISTEMA', loginHeading: 'Entrar',
      loginError: 'Identificador o contraseña incorrectos.', loginEmailLabel: 'Correo electrónico o CPF',
      loginEmailPlaceholder: 'Su correo electrónico o CPF', loginPasswordLabel: 'Contraseña',
      loginSubmit: 'Entrar', loginNoAccount: '¿Todavía no tiene una cuenta?',
      loginRegisterLink: 'Regístrese',

      registrations: 'REGISTROS', authorFormPageTitle: 'Registro de autor',
      authorNewTitle: 'Nuevo autor', authorEditTitle: 'Editar autor',
      authorName: 'Nombre', authorBirthDate: 'Fecha de nacimiento', save: 'Guardar',
      cancel: 'Cancelar', authorsPageTitle: 'Autores', authorsHeading: 'Autores',
      authorAdd: '+ Nuevo autor', nameColumn: 'Nombre', birthDateColumn: 'Fecha de nacimiento',
      actionsColumn: 'Acciones', edit: 'Editar', delete: 'Eliminar',
      authorDeleteConfirm: '¿Eliminar este autor?', authorEmpty: 'No hay autores registrados.',

      publisherFormPageTitle: 'Registro de editorial', publisherNewTitle: 'Nueva editorial',
      publisherEditTitle: 'Editar editorial', publisherCompanyName: 'Razón social',
      cnpjLabel: 'CNPJ (14 dígitos)', addressLabel: 'Dirección', cityLabel: 'Ciudad',
      publishersPageTitle: 'Editoriales', publishersHeading: 'Editoriales',
      publisherAdd: '+ Nueva editorial', cnpjColumn: 'CNPJ', addressColumn: 'Dirección',
      cityColumn: 'Ciudad', publisherDeleteConfirm: '¿Eliminar esta editorial?',
      publisherEmpty: 'No hay editoriales registradas.',

      loanFormPageTitle: 'Registrar préstamo', circulation: 'CIRCULACIÓN',
      loanRegisterHeading: 'Registrar préstamo', userLabel: 'Usuario',
      selectUser: 'Seleccione el usuario', availableCopyLabel: 'Ejemplar disponible',
      selectCopy: 'Seleccione el ejemplar', loanPeriod: 'Plazo (días)',
      confirmLoan: 'Confirmar préstamo', loanNote: 'El sistema calcula la fecha prevista de devolución a partir de la fecha actual y del plazo indicado. El préstamo cambia el estado del ejemplar a PRESTADO.',
      loansPageTitle: 'Préstamos', loansHeading: 'Préstamos y devoluciones',
      loansDescription: 'Registre retiros, plazos, devoluciones y condiciones físicas.',
      newLoan: '+ Nuevo préstamo', bookCopyColumn: 'Libro / ejemplar',
      userColumn: 'Usuario', withdrawalColumn: 'Retiro', deadlineColumn: 'Plazo',
      returnDateColumn: 'Devolución', situationColumn: 'Situación',
      registerReturnColumn: 'Registrar devolución', pending: 'Pendiente',
      loanLate: 'Atrasado', loanOpen: 'Abierto', loanReturned: 'Devuelto',
      damage: 'Daño', damagePlaceholder: 'Observación del daño',
      returnButton: 'Devolver', loanEmpty: 'No hay préstamos registrados.',

      copiesPageTitle: 'Ejemplares', physicalCollection: 'ACERVO FÍSICO',
      copiesHeading: 'Ejemplares', copiesDescription: 'Unidades físicas registradas para los libros.',
      registerCopies: 'Registrar ejemplares', codeColumn: 'Código', bookColumn: 'Libro',
      stateColumn: 'Estado', observationsColumn: 'Observaciones',
      maintenance: 'Mantenimiento', discard: 'Descartar', discardConfirm: '¿Confirmar descarte?',
      copiesEmpty: 'No hay ejemplares registrados.',

      historyPageTitle: 'Historial de la colección', collectionAudit: 'AUDITORÍA DE LA COLECCIÓN',
      historyHeading: 'Historial de movimientos',
      historyDescription: 'Entradas, registros, préstamos, devoluciones, mantenimientos y descartes.',
      movementType: 'Tipo de movimiento', all: 'Todas', filter: 'Filtrar',
      clear: 'Limpiar', dateTimeColumn: 'Fecha y hora', typeColumn: 'Tipo',
      descriptionColumn: 'Descripción', responsibleColumn: 'Responsable',
      historyEmpty: 'No hay movimientos registrados.',

      bookDetailPageTitle: 'Detalles del libro', backToSearch: '← Volver a la búsqueda',
      collectionDetails: 'DETALLES DE LA COLECCIÓN', volumeLabel: 'Volumen', isbnLabel: 'ISBN',
      publisherLabel: 'Editorial', authorsLabel: 'Autor(es)', registeredCopies: 'Ejemplares registrados',
      availableCopies: 'Disponibles', editRegistration: 'Editar registro',
      bookFormPageTitle: 'Registro de libro', collectionManagement: 'GESTIÓN DE LA COLECCIÓN',
      addBookTitle: 'Registrar libro', editBookTitle: 'Editar libro', titleLabel: 'Título',
      volumeNumber: 'Número de volumen', isbnPlaceholder: 'Ej.: 978-...',
      selectPublisher: 'Seleccione una editorial',
      publisherBeforeBook: 'Registre la editorial antes de registrar el libro.',
      selectAuthors: 'Autores (seleccione uno o más)',
      multipleAuthorsHint: 'Para seleccionar varios, mantenga presionada la tecla Ctrl mientras hace clic.',
      totalCopies: 'Cantidad total de ejemplares deseada',
      copiesEditHint: 'Al editar, el sistema agrega ejemplares hasta alcanzar esa cantidad. No elimina los ejemplares existentes.',
      saveBook: 'Guardar libro', recommendedOrder: 'Orden recomendado:',
      recommendedOrderText: 'registre primero los autores y las editoriales; después registre el libro e indique cuántos ejemplares físicos existen.',
      booksPageTitle: 'Buscar libros', collection: 'COLECCIÓN',
      booksHeading: 'Buscar libros', searchInstructions: 'Utilice uno o más campos para filtrar los resultados.',
      addBook: '+ Registrar libro', titleFilter: 'Título', authorFilter: 'Autor',
      publisherFilter: 'Editorial', isbnFilter: 'ISBN', bookTitlePlaceholder: 'Título del libro',
      authorNamePlaceholder: 'Nombre del autor', publisherNamePlaceholder: 'Nombre de la editorial',
      isbnCodePlaceholder: 'Código ISBN', search: 'Buscar', bookColumn: 'Libro',
      authorsColumn: 'Autor(es)', publisherColumn: 'Editorial', actionColumn: 'Acción',
      details: 'Detalles', noBooksFound: 'No se encontraron libros. Ajuste los filtros o registre un libro.',

      usersAdminPageTitle: 'Gestionar usuarios', administration: 'ADMINISTRACIÓN',
      usersHeading: 'Usuarios', usersDescription: 'Consulte los usuarios y registre empleados.',
      registerEmployeeHeading: 'Registrar empleado', emailLabel: 'Correo electrónico',
      cpfLabel: 'CPF (11 dígitos)', cepLabel: 'Código postal (8 dígitos)',
      passwordMinimum: 'Contraseña (mínimo 8 caracteres)', registerEmployee: 'Registrar empleado',
      registeredUsersHeading: 'Usuarios registrados', emailColumn: 'Correo electrónico',
      cpfColumn: 'CPF', cepColumn: 'Código postal', userTypeColumn: 'Tipo',

      userRegisterPageTitle: 'Registro de usuario', newRegistration: 'NUEVO REGISTRO',
      createUserHeading: 'Crear cuenta de usuario',
      publicRegistrationDescription: 'El registro realizado en esta página crea una cuenta de usuario común.',
      fullName: 'Nombre completo', register: 'Registrarse',

      errorPageTitle: 'Error', errorHeading: 'No se pudo completar la operación',
      errorDescription: 'Verifique los datos e inténtelo de nuevo. Si el problema continúa, vuelva a la página de inicio.',
      backToHome: 'Volver al inicio',

      homePageTitle: 'Biblioteca Colección', homeEyebrow: 'SISTEMA DE GESTIÓN DE BIBLIOTECA',
      homeHeading: 'Encuentre su próxima lectura.',
      homeDescription: 'Consulte la colección, verifique la información de los libros y compruebe la disponibilidad de los ejemplares.',
      searchCollection: 'Buscar en la colección', collectionCard: 'Colección',
      collectionCardDescription: 'Registro y organización de libros, autores, editoriales y ejemplares.',
      consultBooks: 'Consultar libros →', researchCard: 'Búsqueda',
      researchCardDescription: 'Busque por título, autor, editorial o ISBN.',
      openSearch: 'Abrir búsqueda →', loansCard: 'Préstamos',
      loansCardDescription: 'Registre retiros, devoluciones, retrasos y daños.',
      manageLoans: 'Gestionar préstamos →', historyCard: 'Historial',
      historyCardDescription: 'Consulte los movimientos realizados en la colección.',
      viewMovements: 'Ver movimientos →', registrationCard: 'Registro',
      registrationCardDescription: 'Cree su cuenta para utilizar los servicios de la biblioteca.',
      registerUser: 'Registrar usuario →', academicPrototype: 'Prototipo académico:',
      prototypeDescription: 'datos almacenados localmente en una base de datos relacional H2. El acceso administrativo debe ser realizado por un empleado.'
    }
  };

  // Tradução de valores de enums produzidos pelo backend.
  const enumTranslations = {
    pt: {
      DISPONIVEL: 'DISPONÍVEL', EMPRESTADO: 'EMPRESTADO',
      MANUTENCAO: 'EM MANUTENÇÃO', DESCARTADO: 'DESCARTADO',
      FUNCIONARIO: 'FUNCIONÁRIO', COMUM: 'COMUM', ADMINISTRADOR: 'ADMINISTRADOR',
      LIVRO_CADASTRADO: 'LIVRO_CADASTRADO', LIVRO_ATUALIZADO: 'LIVRO_ATUALIZADO',
      EXEMPLAR_CADASTRADO: 'EXEMPLAR_CADASTRADO', AUTOR_CADASTRADO: 'AUTOR_CADASTRADO',
      EDITORA_CADASTRADA: 'EDITORA_CADASTRADA', USUARIO_CADASTRADO: 'USUARIO_CADASTRADO',
      EMPRESTIMO: 'EMPRESTIMO', DEVOLUCAO: 'DEVOLUCAO', DESCARTE: 'DESCARTE'
    },
    en: {
      DISPONIVEL: 'AVAILABLE', EMPRESTADO: 'BORROWED',
      MANUTENCAO: 'UNDER MAINTENANCE', DESCARTADO: 'DISCARDED',
      FUNCIONARIO: 'STAFF', COMUM: 'REGULAR USER', ADMINISTRADOR: 'ADMINISTRATOR',
      LIVRO_CADASTRADO: 'BOOK_REGISTERED', LIVRO_ATUALIZADO: 'BOOK_UPDATED',
      EXEMPLAR_CADASTRADO: 'COPY_REGISTERED', AUTOR_CADASTRADO: 'AUTHOR_REGISTERED',
      EDITORA_CADASTRADA: 'PUBLISHER_REGISTERED', USUARIO_CADASTRADO: 'USER_REGISTERED',
      EMPRESTIMO: 'LOAN', DEVOLUCAO: 'RETURN', MANUTENCAO: 'MAINTENANCE', DESCARTE: 'DISCARD'
    },
    es: {
      DISPONIVEL: 'DISPONIBLE', EMPRESTADO: 'PRESTADO',
      MANUTENCAO: 'EN MANTENIMIENTO', DESCARTADO: 'DESCARTADO',
      FUNCIONARIO: 'EMPLEADO', COMUM: 'USUARIO COMÚN', ADMINISTRADOR: 'ADMINISTRADOR',
      LIVRO_CADASTRADO: 'LIBRO REGISTRADO', LIVRO_ATUALIZADO: 'LIBRO ACTUALIZADO',
      EXEMPLAR_CADASTRADO: 'EJEMPLAR REGISTRADO', AUTOR_CADASTRADO: 'AUTOR REGISTRADO',
      EDITORA_CADASTRADA: 'EDITORIAL REGISTRADA', USUARIO_CADASTRADO: 'USUARIO REGISTRADO',
      EMPRESTIMO: 'PRÉSTAMO', DEVOLUCAO: 'DEVOLUCIÓN', MANUTENCAO: 'MANTENIMIENTO', DESCARTE: 'DESCARTE'
    }
  };

  let currentLanguage = 'pt';

  const hasTranslation = (dictionary, key) =>
    Object.prototype.hasOwnProperty.call(dictionary, key);

  const applyLanguage = (requestedLanguage) => {
    currentLanguage = hasTranslation(translations, requestedLanguage)
      ? requestedLanguage
      : 'pt';

    const dictionary = translations[currentLanguage];

    document.querySelectorAll('[data-i18n]').forEach((element) => {
      const key = element.getAttribute('data-i18n');
      if (hasTranslation(dictionary, key)) {
        element.textContent = dictionary[key];
      }
    });

    document.querySelectorAll('[data-i18n-placeholder]').forEach((element) => {
      const key = element.getAttribute('data-i18n-placeholder');
      if (hasTranslation(dictionary, key)) {
        element.setAttribute('placeholder', dictionary[key]);
      }
    });

    document.querySelectorAll('[data-i18n-aria-label]').forEach((element) => {
      const key = element.getAttribute('data-i18n-aria-label');
      if (hasTranslation(dictionary, key)) {
        element.setAttribute('aria-label', dictionary[key]);
      }
    });

    document.querySelectorAll('[data-i18n-title]').forEach((element) => {
      const key = element.getAttribute('data-i18n-title');
      if (hasTranslation(dictionary, key)) {
        element.setAttribute('title', dictionary[key]);
      }
    });

    document.querySelectorAll('[data-i18n-enum]').forEach((element) => {
      // Guarda o valor original uma única vez para permitir alternar entre idiomas.
      if (!element.hasAttribute('data-i18n-enum-original')) {
        const original = element.tagName === 'OPTION'
          ? element.value
          : element.textContent.trim();
        element.setAttribute('data-i18n-enum-original', original);
      }

      const original = element.getAttribute('data-i18n-enum-original');
      const translated = enumTranslations[currentLanguage][original];
      if (translated) {
        element.textContent = translated;
      }
    });

    root.lang = currentLanguage === 'pt' ? 'pt-BR' : currentLanguage;

    if (languageSelect) {
      languageSelect.value = currentLanguage;
    }

    localStorage.setItem('biblioteca-language', currentLanguage);
  };

  // Confirmações traduzidas: os formulários usam data-i18n-confirm no HTML.
  document.querySelectorAll('form[data-i18n-confirm]').forEach((form) => {
    form.addEventListener('submit', (event) => {
      const key = form.getAttribute('data-i18n-confirm');
      const message = translations[currentLanguage][key] || translations.pt[key];
      if (message && !window.confirm(message)) {
        event.preventDefault();
      }
    });
  });

  const savedLanguage = localStorage.getItem('biblioteca-language') || 'pt';
  if (languageSelect) {
    languageSelect.addEventListener('change', (event) => {
      applyLanguage(event.target.value);
    });
  }
  applyLanguage(savedLanguage);

  // Preferências de acessibilidade existentes.
  const applySaved = () => {
    body.classList.toggle('dark-mode', localStorage.getItem('biblioteca-dark') === 'true');
    body.classList.toggle('high-contrast', localStorage.getItem('biblioteca-contrast') === 'true');

    const storedScale = Number(localStorage.getItem('biblioteca-font') || '100');
    const scale = Number.isFinite(storedScale) ? Math.min(150, Math.max(80, storedScale)) : 100;
    root.style.fontSize = `${scale}%`;
  };

  applySaved();

  document.getElementById('darkToggle')?.addEventListener('click', () => {
    const next = !body.classList.contains('dark-mode');
    localStorage.setItem('biblioteca-dark', String(next));
    applySaved();
  });

  document.getElementById('contrastToggle')?.addEventListener('click', () => {
    const next = !body.classList.contains('high-contrast');
    localStorage.setItem('biblioteca-contrast', String(next));
    applySaved();
  });

  document.getElementById('fontIncrease')?.addEventListener('click', () => {
    const scale = Math.min(150, Number(localStorage.getItem('biblioteca-font') || '100') + 10);
    localStorage.setItem('biblioteca-font', String(scale));
    applySaved();
  });

  document.getElementById('fontDecrease')?.addEventListener('click', () => {
    const scale = Math.max(80, Number(localStorage.getItem('biblioteca-font') || '100') - 10);
    localStorage.setItem('biblioteca-font', String(scale));
    applySaved();
  });

  document.getElementById('speakButton')?.addEventListener('click', () => {
    if (!('speechSynthesis' in window)) {
      alert(translations[currentLanguage].speakUnsupported);
      return;
    }

    window.speechSynthesis.cancel();
    const main = document.querySelector('main');
    const text = main ? main.innerText : document.body.innerText;
    const utterance = new SpeechSynthesisUtterance(text);
    utterance.lang = currentLanguage === 'en'
      ? 'en-US'
      : currentLanguage === 'es'
        ? 'es-ES'
        : 'pt-BR';
    window.speechSynthesis.speak(utterance);
  });
})();
