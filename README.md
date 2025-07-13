# WAREHOUSE PAT

Il progetto implementa una semplice applicazione cli che permette di gestire un magazzino di scarpe, pantaloni e maglie.
I dati vengono salvati in un database PostgreSQL che runna in un container Docker.

Per l'esecuzione del programma e' necessario avere docker-compose e maven installati.

Step per l'esecuzione:
- da console, portarsi nella directory del progetto
- eseguire `docker-compose up -d`
- eseguire `mvn compile` per compilare il progetto 
- eseguire `mvn test` per runnare i test, va tenuto in mente che alla fine dei test il database verrà svuotato.
- eseguire `mvn exec:java` per eseguire il programma e interagirci da cli.  