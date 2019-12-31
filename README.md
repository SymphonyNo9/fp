# fp

## sbt
### project & target
> Every project in SBT has a target directory. That's where its compiled classes and other generated things go.
Your root is a project, and TARGET 3 in your diagram is its target.
Your build definition (the project directory) is also a project. With SBT it is possible to write scala code to implement build-related tasks and settings. That compiled code has to go somewhere. It goes in the directory you labeled as TARGET 2 - project/target.
Build definitions in SBT can be recursive, i.e. your build definition can have its own build definition. Since you are using plugins (defined in project/plugins.sbt), your build definition needs a build definition, which ends up being compiled to project/project/target aka TARGET 1 in your diagram.
When you run clean in the SBT console, it will clean files from the current project's target directory. I don't think it's meant to delete the entire directory, but I could be wrong there. In any case, running clean while you have the root project selected should only affect the root project's target.
In the SBT console, you can run reload plugins or reload return to jump into and out of (respectively) the current project's build definition. Calling clean within that context would clean their respective targets.
As for combining them into a single, easily-removed directory, I'm not sure I see the value in that. Having used SBT for several years now, the various target dirs haven't ever really been in the way. I don't think I've even wanted to delete the target directory a single time in the last year or so.