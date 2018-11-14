# DribbbleClient
Android Client for dribbble, written in kotlin.


## The project is stopped and never finished
The project has to be shutdown due to the API restriction:
In API doc:
http://developer.dribbble.com/v2/shots/

The list shots API can only list the authenticated user’s shots. (But I don't have shots in my account.)

The popular shots API has this note:
"Note: This is available only to select applications with our approval."

It will get 403 forbidden response in this project.

So we can't get any shots to show in this project.
That's the reason for shutdown.

I also thought about changing to API v1, but it could not work too.
The get user API will get response:
```
{
    "message": "This version of the API is deprecated. This application must use v2: http://developer.dribbble.com/v2/"
}
```



## For Code Style check
[ktlint](https://ktlint.github.io/) is used for static code checking.
Run:
```
./gradlew ktlint
```
to check.

If any error reported, run:
```
./gradlew ktlintFormat
```
to fix errors.
Note: not all errors can be fixed automatically, in that case, a manual fix is needed.