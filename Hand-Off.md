**git repository is cloned in  /srv/www/
**audio files are saved to /home/ec2-user/audio
**service is set up as micronophones so to manipulate service use:
    sudo service micronophones start
    sudo service micronophones stop
    sudo service micronophones restart
**service definition is in /etc/init.d/micronophones.  it is set up with very basic logic and could use additional logic
  to deal with times when the server is rebooted without properly shutting down the service.
**ssl certificate is in place using letsencrypt.  http traffic is automatically redirected to https.  
**apache is set to forward traffic to port 8080, which the application runs on.  havne't managed to get the springboot 
  embedded tomcat receive the traffic properly yet.
**update.sh located in /home/ec2-user/ will pull from github (prompts for credentials) then build and deploy with any changes.
**build.sh is located in the repository and should pull and configure most everything for the server but I've had limited opportunity
  to test it.  This should be updated with any additional server configuration settings determined to be necessary to make it
  straight forward and easy to move to a new server.  The script should be placed in the home directory and executed with sudo permissions.
**PostgreSQL installed on ec2 with:
	sudo yum install postgresql postgresql-server postgresql-devel postgresql-contrib postgresql-docs
**Create ec2 PostgreSQL database instance and connect with:
	sudo service postgresql initdb
	sudo su - postgresq
	psql -U postgres
	

