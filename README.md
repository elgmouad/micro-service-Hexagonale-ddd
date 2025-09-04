# Microservices avec Architecture Hexagonale et DDD

Ce projet est une application de démonstration qui met en œuvre une architecture de microservices en utilisant les principes de l'Architecture Hexagonale (Ports & Adapters) et du Domain-Driven Design (DDD). Il se compose de deux microservices : `order-service` et `payment-service`, qui communiquent de manière asynchrone via des événements Kafka.

## Architecture

L'application suit une architecture hexagonale pour découpler le cœur de métier (domaine et application) des détails d'infrastructure (web, persistance, messagerie).

- **Domaine :** Contient la logique métier et les modèles du domaine (Entités, Value Objects, Agrégats).
- **Application :** Orchestre les cas d'utilisation (Use Cases) et définit les ports (interfaces) pour les dépendances externes.
- **Infrastructure :** Fournit les implémentations concrètes des ports (adaptateurs) pour les technologies externes comme Spring Web, Spring Data JPA, et Spring Kafka.

## Services

- **Order Service (`order-service`):**
  - Gère la création et la consultation des commandes.
  - Expose une API REST pour interagir avec les commandes.
  - Publie un événement `order-created` sur un topic Kafka lorsqu'une nouvelle commande est créée.

- **Payment Service (`payment-service`):**
  - Écoute les événements `order-created` depuis Kafka.
  - Simule le traitement du paiement pour la commande reçue.
  - Expose une API REST pour vérifier le statut du paiement d'une commande.

## Prérequis

Avant de commencer, assurez-vous d'avoir installé les outils suivants sur votre machine :
- Java 17 ou supérieur
- Apache Maven 3.6 ou supérieur
- Docker et Docker Compose

## Démarrage du projet

Pour lancer l'application complète avec ses dépendances (Kafka, Zookeeper), suivez ces étapes :

1.  **Cloner le dépôt :**
    ```sh
    git clone <url-du-repo>
    cd microservices-ddd-hexagon
    ```

2.  **Compiler le projet :**
    Utilisez Maven pour compiler les deux services et créer les fichiers JAR.
    ```sh
    mvn clean install
    ```
    Cette commande va compiler le code, exécuter les tests et packager les applications.

3.  **Lancer l'environnement avec Docker Compose :**
    Une fois les projets compilés, vous pouvez démarrer tous les services en utilisant Docker Compose.
    ```sh
    docker-compose up --build
    ```
    Cette commande va :
    - Construire les images Docker pour `order-service` et `payment-service`.
    - Démarrer les conteneurs pour Zookeeper, Kafka, `order-service`, et `payment-service`.

4.  **Vérifier que les services sont en cours d'exécution :**
    - `order-service` sera disponible sur `http://localhost:8080`
    - `payment-service` sera disponible sur `http://localhost:8081`

## Utilisation

Une fois les services démarrés, vous pouvez interagir avec l'API REST de `order-service` pour créer une commande.

**Exemple de requête pour créer une commande :**
```sh
curl -X POST http://localhost:8080/orders \
-H "Content-Type: application/json" \
-d '{
    "customerId": "f47ac10b-58cc-4372-a567-0e02b2c3d479",
    "items": [
        {
            "productId": "a1b2c3d4-e5f6-7890-1234-567890abcdef",
            "quantity": 2,
            "price": 25.50
        },
        {
            "productId": "fedcba98-7654-3210-fedc-ba9876543210",
            "quantity": 1,
            "price": 10.00
        }
    ]
}'
```
La création de la commande déclenchera un événement Kafka, qui sera consommé par le `payment-service` pour traiter le paiement.

## Tests

Le projet inclut des tests unitaires et d'intégration pour chaque service. Pour exécuter les tests, utilisez la commande Maven suivante à la racine du projet :

```sh
mvn test
```
Cette commande exécutera tous les tests pour les modules `order-service` et `payment-service`.
