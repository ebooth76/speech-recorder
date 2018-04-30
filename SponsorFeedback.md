
User Interface
--------------

* The user interface is great.  The highest priority at this point is to make the recording interface more kid friendly by using a simpler background, and larger text and buttons.


Database
--------

* PostgreSQL database is currently being used to store and randomize prompt information
* Eventually the database should store user information and audio recordings


Audio Files
-----------

* Audio files are being stored locally in the /home/ec2-user/audio/ folder of the web server
* Audio files should be stored in wav 16bit 16kHz mono, but it appears they are being stored in Ogg Opus format, 32bit 48kHz stereo
* Audio recordings longer than 65536 are throwing a Java exception and are not being stored
> org.springframework.messaging.simp.stomp.stomp.StompConversionException: The 'content-lenght' header 72166 exceeds the configured message buffer size limit 65536
* Since the database is not supporting the logging of audio recordings, a quick fix workaround would be to encode the user info into the file names and store two files, one for audio and another for the text contained in the prompt
> File format: GenderAgeTimestamp.<wav|txt>


