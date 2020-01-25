# Fully working website for posting and viewing recipes.

## Used: Javalin, Maven, PostgreSQL, JUnit, Sentry, Jbcrypt, Jsoup, Swagger, Jetty(sessions), Javax (Mail, Activation), Commons (IO, FileUpload) and etc.

## The site was developed using Java (Javalin) on the backend and JavaScript on the frontend.
![Image alt](https://github.com/emmilaze/Website/raw/master/pics/main.png)

## Site features:
+ ## Authorization, registration.
  + ## Authorization:
    ![Image alt](https://github.com/emmilaze/Website/raw/master/pics/loginPage.png)
    
  + ## Registration:
  ![Image alt](https://github.com/emmilaze/Website/raw/master/pics/registration.png)
  
+ ## Create \ view \ edit \ download the recipes.
  + ## Create:
  ![Image alt](https://github.com/emmilaze/Website/raw/master/pics/create1.png)
  ![Image alt](https://github.com/emmilaze/Website/raw/master/pics/create2.png)
  ![Image alt](https://github.com/emmilaze/Website/raw/master/pics/create3.png)
  
  + ## View: 
  ![Image alt](https://github.com/emmilaze/Website/raw/master/pics/recipe.png)
  ![Image alt](https://github.com/emmilaze/Website/raw/master/pics/recipe2.png) 
  ![Image alt](https://github.com/emmilaze/Website/raw/master/pics/recipe3.png)
  
  + ## Edit: 
  ![Image alt](https://github.com/emmilaze/Website/raw/master/pics/edit.png)
  
  + ## Download:
  ![Image alt](https://github.com/emmilaze/Website/raw/master/pics/download.png)
  
+ ## "Like" system.
+ ## The ability to share on social networks \ messengers.
  ![Image alt](https://github.com/emmilaze/Website/raw/master/pics/like.png)
  
+ ## Ability to post and view comments.
  ![Image alt](https://github.com/emmilaze/Website/raw/master/pics/future_comment.png)
  ![Image alt](https://github.com/emmilaze/Website/raw/master/pics/comment.png)
    
+ ## The ability to sort recipes on any page where they are displayed by popularity or by novelty.
+ ## Search by name and \ or by ingredients. Fuzzy search is used!
![Image alt](https://github.com/emmilaze/Website/raw/master/pics/search.png)
![Image alt](https://github.com/emmilaze/Website/raw/master/pics/search_result.png)

+ ## Ability to leave feedback.
![Image alt](https://github.com/emmilaze/Website/raw/master/pics/report.png)

+ ## The ability to view past versions of the recipe (after editing).
![Image alt](https://github.com/emmilaze/Website/raw/master/pics/versions.png)

+ ## Recipe editing is possible in all respects.
+ ## User tier system: 
  + ## Only registered users can create recipes, leave likes and comments.
  + ## Recipes of newly created users are moderated by the administration (moderators or administrators). So recipes may be subject to editing by the administration.
  + ## Newly created users are required to confirm their mail for full access to the site.
  + ## Users can view / edit / delete all recipes they create, even if they have not yet been confirmed by the administration.
  + ## Users can view a list of recipes that they like.
  + ## On the profile page, user can change the password for the account.
  ![Image alt](https://github.com/emmilaze/Website/raw/master/pics/changePassword.png)
  
  + ## Moderators and administrators have access to the page with unconfirmed recipes, where they can view and confirm them.
  ![Image alt](https://github.com/emmilaze/Website/raw/master/pics/approve.png)
  ![Image alt](https://github.com/emmilaze/Website/raw/master/pics/approve_button.png)
  
  + ## Only recipes and administrators can modify and delete recipes. Moderators to edit and delete do not have authority.
  + ## Administration can not "like" the recipes.
+ ## Only administrators can access the administration page, where administrator can view:  
  + ## A report on the number of recipes downloaded.
  + ## A report on the date and number of recipes created on that day.
![Image alt](https://github.com/emmilaze/Website/raw/master/pics/admin_page.png)

  + ## Creation and tracking of recipes categories, and a list of all registered users.
![Image alt](https://github.com/emmilaze/Website/raw/master/pics/category.png)

  + ## Administration can block any user. A blocked user has the same features as an unregistered user.
  ![Image alt](https://github.com/emmilaze/Website/raw/master/pics/users.png)
