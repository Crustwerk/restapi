package com.crustwerk.restapi.repository;

import com.crustwerk.restapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
    Spring Data JPA è una parte del progetto Spring Data.

        - Spring Data è un framework che semplifica l'accesso ai dati,
        fornendo astrazioni per lavorare con database relazionali, NoSQL, servizi REST, ecc.

        - Spring Data JPA è l’estensione specifica per lavorare con JPA (Java Persistence API),
        lo standard Java per la gestione di database relazionali tramite ORM (Object-Relational Mapping).

        - Internamente, Spring Data JPA utilizza Hibernate (come provider JPA di default, ma è possibile usarne altri)
        per trasformare gli oggetti Java in query e gestire il mapping verso le tabelle del database.

    In sintesi:
        1) Spring Data JPA genera le query JPQL dai nomi dei metodi nell’interfaccia Repository

        2) Hibernate traduce quelle query JPQL in SQL comprensibile per il database specifico.

        3) JDBC si occupa della comunicazione effettiva con il database:
        invia la query SQL, riceve i risultati, gestisce connessioni e transazioni.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /*
    Qui è possibile dichiarare dei metodi e Spring Data si occuperà di ricavarne la query appropriata.
        Es.
        Optional<User> findByUsername(String username);

    QUERY DERIVATION:
    Processo tramite il quale Spring Data JPA estrae automaticamente la query da eseguire
    dal nome del metodo dichiarato nell'interfaccia.
    */
}