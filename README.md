💳 Gestion des Cartes Bancaires & Détection de Fraude
📌 Contexte du projet

La gestion des cartes bancaires et la détection de fraude sont devenues des priorités majeures pour les banques.

Chaque client possède une ou plusieurs cartes (débit, crédit, prépayée) qui génèrent des transactions diverses (paiement, retrait, achat en ligne).

Les banques doivent donc :

🔄 Gérer le cycle de vie d’une carte (création, activation, suspension, renouvellement).

⏱️ Suivre en temps réel les opérations liées aux cartes.

🕵️ Détecter automatiquement des comportements suspects (ex : achats dans deux pays différents à quelques minutes d’intervalle).

🚨 Alerter les responsables ou bloquer la carte en cas de fraude potentielle.

🏗️ Architecture de l’application
1. Couche Présentation (UI/Menu)

Interface textuelle avec navigation par menu.

2. Couche Métier (Services)

Logique applicative pour : gestion des cartes, transactions et alertes de fraude.

3. Couche Entity

Objets persistants :

Client

Carte (Débit, Crédit, Prépayée)

OperationCarte

AlerteFraude

Utilisation de :

record → OperationCarte (immutabilité).

sealed → Carte (héritage restreint).

4. Couche DAO

Gestion CRUD pour clients, cartes, opérations et alertes.

5. Couche Utilitaire

Vérification des règles de fraude.

Gestion des dates et lieux.

Génération de numéros de carte uniques (simulés).

📦 Contenu des Classes
Entity (Modèle)

Client (record) : id, nom, email, téléphone

Carte (sealed class) : id, numero, dateExpiration, statut (ACTIVE, SUSPENDUE, BLOQUEE), idClient

CarteDébit : plafond journalier

CarteCrédit : plafond mensuel, taux d’intérêt

CartePrépayée : solde disponible

OperationCarte (record) : id, date, montant, type (ACHAT, RETRAIT, PAIEMENT_EN_LIGNE), lieu, idCarte

AlerteFraude (record) : id, description, niveau (INFO, AVERTISSEMENT, CRITIQUE), idCarte

DAO

ClientDAO : CRUD client

CarteDAO : CRUD carte, recherche par client

OperationDAO : CRUD opération, recherche par carte, filtrage par type/date

AlerteDAO : CRUD alerte, recherche par carte

Services (Logique Métier)

ClientService : gestion des clients, recherche par email/téléphone.

CarteService : création, activation, blocage, gestion des plafonds.

OperationService : enregistrement et recherche des opérations.

FraudeService : détection d’anomalies (montants élevés, lieux différents, dépassement de plafond) + génération d’alertes.

RapportService : statistiques, top 5 des cartes les plus utilisées, cartes bloquées/suspectes.

ImportExportService : import de fichiers Excel (opérations/cartes).

🖥️ Interface Utilisateur

Menu textuel permettant de :

Créer un client.

Émettre une carte (débit, crédit, prépayée).

Effectuer une opération (achat, retrait, paiement en ligne).

Consulter l’historique d’une carte.

Lancer une analyse des fraudes.

Bloquer/suspendre une carte.

🗄️ Base de Données (MySQL via JDBC)

Client : id, nom, email, téléphone.

Carte : id, numero, dateExpiration, statut, typeCarte, idClient.

OperationCarte : id, date, montant, type, lieu, idCarte.

AlerteFraude : id, description, niveau, idCarte.

Relations :

1..n → Client ⇢ Carte

1..n → Carte ⇢ OperationCarte

1..n → Carte ⇢ AlerteFraude

⚙️ Exigences Techniques

Java 17 : record, sealed, Stream API, Optional.

JDBC + MySQL

Architecture en couches (Entity, DAO, Service, UI).

Gestion des exceptions.

Utilisation de Git (commits réguliers, clairs et bien décrits).

JAR exécutable.

📊 Évaluation & Critères de Performance

Application développée en Java 17.

Respect de l’architecture en couches.

Bonne utilisation de la programmation fonctionnelle (Stream, Optional, Lambda).

Connexion à la base de données via JDBC.

Code propre, commenté et conforme aux conventions Java.

README.md complet et structuré (ce fichier).

Diagramme UML fidèle reflétant la structure réelle du projet.