# CS471-F17-Micronophones

## Team Name: Micronophones

## Sponsor Name: Eric Booth

## Project Description:

At-home speech therapy device

For children and adults with speech disabilities, speech therapy can be very expensive
and time consuming, especially for those who do not live in close proximity to a
therapist. Micron would like to enlist a student team to build a user interface and
database for an at-home speech therapy assistance tool that will prompt for a specific
word or phrase, record the response, use an open-source automatic speech recognition
toolkit to score the response, display the score to the speaker, and then store the
recorded audio and scoring information in a database.

The database management system and ASR toolkit will be selected from off-the-shelf
open source software. Our task will be to develop an interface between the user, the
ASR, and the database.

## Team Members:

Last Name       | First Name      | GitHub User Name     | Scrum Role
--------------- | --------------- | -------------------- | --------------- 
Black             | Stacy             | stacyblack0                  | Developer
Eddy             | Marshall             | MarshallEddy                  | Developer
Hanic            | Edin             | edinhanic                  | Developer
Henggeler             | Peter             | phenggeler                  | Developer
Krahn             | Jacob             |    jakelar               | Developer
Manning             | Tyler             | sylsr                  | Product Owner
Turulja           | Eldin             | EldinTuru                  | Developer
Wamsley             | Aaron             | aaronwamsley                  | Developer
Ward             | Alec             | AlecWard                  | Developer
Westmoreland             | Lucas             | lucaswestmoreland                  | Scrum Master

## Team Velocity:

Sprint | Estimated Velocity | Actual Velocity
------ | ------------------ | ---------------
1  | 18               | TBD
2  | TBD                | TBD
3   | TBD                | TBD
4  | TBD                | TBD

# java-getting-started

[![CircleCI](https://circleci.com/gh/heroku/java-getting-started.svg?style=svg)](https://circleci.com/gh/heroku/java-getting-started)

A barebones Java app, which can easily be deployed to Heroku.

This application supports the [Getting Started with Java on Heroku](https://devcenter.heroku.com/articles/getting-started-with-java) article - check it out.

[![Deploy to Heroku](https://www.herokucdn.com/deploy/button.png)](https://heroku.com/deploy)

## Running Locally

Make sure you have Java and Maven installed.  Also, install the [Heroku CLI](https://cli.heroku.com/).

```sh
$ git clone https://github.com/heroku/java-getting-started.git
$ cd java-getting-started
$ mvn install
$ heroku local:start
```

Your app should now be running on [localhost:5000](http://localhost:5000/).

If you're going to use a database, ensure you have a local `.env` file that reads something like this:

```
DATABASE_URL=postgres://localhost:5432/java_database_name
```

## Deploying to Heroku

```sh
$ heroku create
$ git push heroku master
$ heroku open
```

## Documentation

For more information about using Java on Heroku, see these Dev Center articles:

- [Java on Heroku](https://devcenter.heroku.com/categories/java)
