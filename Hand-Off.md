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
