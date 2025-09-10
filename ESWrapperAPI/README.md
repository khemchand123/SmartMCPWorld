nohup java -jar /home/khem-chand/work/ElasticSearchWrapper/target/ElasticSearchWrapper-1.0-SNAPSHOT.jar > app.log 2>&1 &

scp -i /home/khem-chand/Downloads/elastic_kibana_key /home/khem-chand/work/ElasticSearchWrapper/target/ElasticSearchWrapper-1.0-SNAPSHOT.jar khem@54.66.84.237:/home/khem/


nohup /home/khem-chand/software/ngrok http --domain=goshawk-excited-basically.ngrok-free.app 8080 > ngrok.log 2>&1 &

mvn clean install

