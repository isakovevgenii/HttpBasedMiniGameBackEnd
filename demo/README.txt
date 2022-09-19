  1)Ability to make the URL for com.sun.net.httpserver your kind, for example: /<userid>/login.
  I find it more convenient to navigate with URLs of my type, and frankly I'm not sure if com.sun.net.httpserver has such a feature.

  Example request:
                    * GET  http://localhost:8000/login?userId=2
                    * POST http://localhost:8000/score?sessionKey=If8vaccAVd&levelId=3
                    * GET  http://localhost:8000/highScoreList?levelId=3

2)It would be interesting to know what exactly you meant by 31 bit unsigned integer number.
  I made these variables as int type, hence now they will take 4 bytes each.
  I think there is a mystery here on your part:) Maybe you wanted to see my conversion and binary conversion skills,
  but 3 hours on this assignment didn't seem like enough time for that.

3)As for testing - covered the basic functions with tests.
  Mainly tested this application with Postman. I love it, here is a great mvp case for the demo:

  https://www.getpostman.com/collections/f8def5663feac450a731

4)Consideration of concurrency issues is not implemented to the max. Made all the handles synchronized.
  Of course it could be done much nicer, but the time was beyond 3 hours. Please be understanding:)