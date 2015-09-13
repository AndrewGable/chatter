## Chatter
The Chatter Web Project was developed in the Cloud Computing course at Boise State University.
It was deployed on three separate Ubuntu 14.04 virtual machines, with one database virtual server, 
and a load balancer server. The load balancer used <a href="http://www.haproxy.org/">HAProxy</a> to 
keep sticky sessions between the load balanced servers. The project was deployed on Tomcat 8 with a
Jersey servlet and a JAX-RS RESTful API. The back end used Hibernate with a MySQL database. The front
end implemented <a href="http://purecss.io/">PureCSS</a> and JQuery.</p>

The project was broken up into three separate services: friend service, tweet service, and monitor 
service. The friend service, and tweet service were additionally load balanced using a queuing system
to respond to requests.

<p align="center"><img width="500" src="http://andrewcode.com/img/chatterHome.png"></p>

The monitor service keeps stats on all different types of information about Chatter services running.
It displayed them for each individual load balanced servers in a dashboard. The dashboard used the
<a href="http://www.chartjs.org/">Chart.js</a> library to chart statistics in a responsive manner.

<p align="center"><img width="400" src="http://andrewcode.com/img/chatterDashboard.png"></p>

<hr>

### Project Structure:

- `pom.xml` - Various build dependencies and plugins used to build Chatter
- `ChatterDB2.sql` - Database table builds
- `com.andrewcode.queue`
  - Controllers
  	- `MonitorService.java` The monitor service that returns the rest values
	- `TaskQueue.java` The worker tasks queue

  - Models
 	- `Worker.java` The worker that finishes all the work items

  - Utils
	- `ProcessingFactory.java` The factory that processes the work items
	- `ServletContextListener.java` The listener that sets up the queue
	- `ResolutionException.java` When you pass in a wrong value for the qps resolution this is thrown
	- `WorkItem.java` Worker item interface for work items

  - WorkItems
	- `CreateFriendship.java` Implemented work item 
	- `GetIncomingFriends.java` Implemented work item 
	- `Logout.java` Implemented work item 
	- `CreateUser.java` Implemented work item 
	- `GetOutgoingFriends.java` Implemented work item 
	- `PostTweet.java` Implemented work item 
	- `DestroyFriendship.java` Implemented work item 
	- `GetTweet.java` Implemented work item 
	- `RetweetTweet.java` Implemented work item 
	- `DestroyTweet.java` Implemented work item 
	- `GetTweetList.java` Implemented work item 
	- `UsernameLogin.java` Implemented work item 
	- `GetFollowers.java` Implemented work item 
	- `GetUsername.java` Implemented work item 
	- `GetFollowing.java` Implemented work item 
	- `IdLogin.java` Implemented work item 

- `com.andrewcode.rest`
  - Controllers
    - `FriendService.Java` Handles all of the friendships and followers rest calls
    - `TweetService.java` Handles all of the tweet related rest calls
    - `UserService.java` Handles the creation and login of users
  - Models
    - `Friends.java` Hibernate annotated model of table in MySql database
    - `Tweet.java` Hibernate annotated model of table in MySql database
    - `User.java` Hibernate annotated model of table in MySql database
    - `Query.java` Hibernate annotated model of table in MySql database
    - `Error.java` Hibernate annotated model of table in MySql database
  - Util
    - `FriendException.java` Exception handling for `FriendService.java`
    - `TweetException.java` Exception handling for `TweetService.java`
    - `UserException.java` Exception handing for `UserService.java`
    - Utils.java Static helper methods that I did not want to copy over and over again
- Resources
    - `hibernate.cfg.xml` Hibernate connector for hibernate orm library
- Webapp
  - css
    - `jquery.modal.css` CSS files for modals I am using in Views
    - `pure-min.css CSS` framework used in all views of Chatter
  - img
    - `close.png` Close icon used for modals
    - `spinner.gif` Spinner gif used for modals
    - `Bridge.png` Home page photograph
  - js
    - `jquery.modal.min.js` Modal javascript code used for modals in Chatter
    - `jquery-2.1.1.min.js` jQuery used for many post and get calls
    - `Chart.js Javascript` library for charts used in the dashboard
  - WEB-INF
    - `web.xml` Configuration file for Chatter
  - `about.html` About me page
  - `friends.html` FriendService testing page includes all posts and get tests
  - `index.html` UserService testing page includes login, create and find users tests
  - `tweet.html` TweetService testing page includes all tweet method tests
  - `dashboard.html` Testing page for the newly added monitor
  - `monitor.html` Front facing page to get you to each individual node's dashboard

### Server Layout:

#### Load Balancer - 
	CS597-Andrew-Balancer
	andrew@132.178.129.1

#### Database - 
	CS597-Andrew-Database
	andrew@132.178.129.2

#### Child Nodes - 
	CS597-Andrew-One
	andrew@132.178.128.219

	CS597-Andrew-Two
	andrew@132.178.129.250

	CS597-Andrew-Three
	andrew@132.178.129.229

### Database Build:

1. Create a user in mysql with username = root and password = pass
2. Create a database called chatterDB
(mysqladmin -uroot -ppass create chatterDB)
3. Create all the tables for chatterDB
(mysql -uroot -ppass chatterDB < ChatterDB2.sql)

Must then create a username = andrew and password = pass 
with access to database from the child node ip's. 

i.e. The users "Andrew" have access from the host

	andrew           | 132.178.128.219       |
	andrew           | 132.178.129.229       |
	andrew           | 132.178.129.250       |

### Project Build:

1. Run 'mvn clean package'
2. Change directories to 'target/'
3. Project was built as 'ROOT.war'
(Why ROOT.war? It enables me to deploy Chatter to local host root and not as localhost:8080/Chatter/
*Please* keep this the same because all jQuery calls are assuming this naming convention.)
4. Copy 'ROOT.war' to tomcat deployment directory or deploy using the tomcat manager of each child node
5. Deploy the haproxy.cfg and start ha proxy up
6. Browse to root (http://CS597-Andrew-Balancer) and Chatter with the monitor should load (Should see a big picture of a bridge)


### Project Design:

The existing chatter project will look very similar, but the design of the servers and scaling is very different. The monitor was not very trivial to set up. The first thing I had to do was set up a global database that the three different nodes could connect to. Implementing the load balancer was easy once I figured out how to tell HA Proxy to create a "SEVERID" cookie on the client. This will keep the session the same when accessing the load balancing server. All of this engineering should be completely invisible to the user because of the sticky sessions. 

The monitor server is started with the 'queue/' prefix to the url to designate the difference from the rest calls. They accept a 'GET' request with @PathParam annotations. All methods return JSON on success and a WebApplicationException with an error message on an error.

All rest calls have 'rest/' appended to the url to designate rest calls from other web pages in Chatter. All 'POST' calls use the @FormParam and all 'GET' calls use the @PathParam annotations. The project is broken up into three simple services UserService, FriendService, and TweetService. All methods return JSON on success and a WebApplicationException with an error message on an error.

- `MonitorService.java`
    - `GET` `monitor/processingtime` - Returns an array of average response times in a 10 percentile range
	- `GET` `monitor/queuedepth` - Returns the number of elements in the worker task queue
	- `GET` `monitor/qps/{resolution}` - Accepts "minutes", "hours", "days", or "months" only. Returns the count of messages past 100 <resolution> from now. I.e. user types "minutes" and get the count of messages 100 minutes from now.
	- `GET` `monitor/errors/{type}` - Accepts a valid HTTP error code, only 400 will return a non-zero. Will return a count of the valid error codes passed in. 

- `UserService.java`
    - `POST` `users/create/{user-name}` - Create a user from specific user name
    - `GET` `users/{users-name}` - Get a userId from a user name
    - `POST` `users/login/{userId}` - Login passes a userId to set for the session

- `FriendService.Java`
    - `GET` `friendships/incoming` - Returns the users incoming friendship requests
    - `GET` `friendships/outgoing` - Returns the users outgoing friendship requests
    - `POST` `friendships/create` - Create a friendship requests if you'd like to accept a friendship request, send a create to that user id
    - `POST` `friendships/destroy` - Destroys a friendship completely. Assumed this was a 'unfriend' or 'block' and not just an unfollow
    - `GET` `friends/list` - Returns a list of userIds of people who the logged in user is following
    - `GET` `followers/list` - Returns a list of userIds of people who are following the logged in user

- `TweetService.java`
    - `POST` `tweet/tweet/{msg}` - Create a tweet that is less that 128 character from passed in message
    - `GET` `tweet/show/{id}` - Get a tweet from a specific id
    - `POST` `tweet/destroy/{id}` - Delete a tweet from a specific id
    - `POST` `tweet/retweet/{id}` - Retweet takes the tweet message, posts it again with a new tweetId and assigns the userId to the user who created the retweet.

- Additional Rest End Points:

    - `POST` `users/login/{user-name}` - Login passes a *user-name* instead of a userId because it is easier for a user to remember a user name then a id
    - `GET` `tweet/getTweets` - Returns a list of tweet objects for a users feed (i.e. the tweets from the user or tweets from the people the user is following)
    - `POST` `users/logout` - Invalidates the users session (Really handy for testing multiple relationships between multiple
    users)

### Comments:

This project was much more challenging then the previous project. I learned a lot about cookies, sessions,and load balancing with the addition of the HAProxy load balancer. I also continued my knowledge of javascript by creating a dash board for the individual servers. I learned a lot about different design patterns and why the one we chose was the best for us. The individual worker task queue is fast, and it leaves less refactoring for us with our existing service. I still need to learn how to secure the server more (XSS, SQL injection) to name a few. Also I will need to learn more AJAX because the dashboard is a bit primitive without AJAX.

### Links:

- http://blog.haproxy.com/2012/03/29/load-balancing-affinity-persistence-sticky-sessions-what-you-need-to-know/
- https://serversforhackers.com/editions/2014/07/15/haproxy/
- http://www.chartjs.org/
- http://purecss.io/
- http://www.rackspace.com/knowledge_center/article/mysql-connect-to-your-database-remotely
