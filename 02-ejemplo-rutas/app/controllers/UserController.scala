package controllers



import entities.{User, UserForm}
import javax.inject._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

import scala.collection.mutable.ListBuffer

/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */
@Singleton
class UserController @Inject()(
            mcc: MessagesControllerComponents
            ) extends MessagesAbstractController(mcc) {

  private val logger = play.api.Logger(this.getClass)

  var listado: ListBuffer[User]= new ListBuffer[User]()
  for (i <- 1 to 5){
    listado += new User(i,"Pepe" + i)
  }
  val form = Form(
    mapping(
      "name" -> nonEmptyText,
    )(UserForm.apply)(UserForm.unapply)
  )
  /**
    * Create an Action to render an HTML page with a welcome message.
    * The configuration in the `routes` file means that this method
    * will be called when the application receives a `GET` request with
    * a path of `/`.
    */
  def index = Action {
    // Presenta un dato
    Ok(views.html.usuarioIndex("Página de Usuario"))
  }
  def list = Action {
    // Presenta un listado

    Ok(views.html.usuarioList(listado))
  }

  private val saveUrl = routes.UserController.save()
  def addForm = Action { implicit request =>
    // pass an unpopulated form to the template
    Ok(views.html.usuarioForm(form, saveUrl))
  }
  def save = Action { implicit request =>
    val errorFunction = { formWithErrors: Form[UserForm] =>
      // this is the bad case, where the form had validation errors.
      // show the user the form again, with the errors highlighted.
      BadRequest(views.html.usuarioForm(formWithErrors, saveUrl))
    }

    val successFunction = { data: UserForm =>
      // this is the SUCCESS case, where the form was successfully parsed as a BlogPost
      val user = new User(listado.length+1,data.name)
      listado.append(user)
      Redirect(routes.UserController.list()).flashing("info" -> "Blog post added (trust me)")
    }

    val formValidationResult: Form[UserForm] = form.bindFromRequest
    formValidationResult.fold(
      errorFunction,   // sad case
      successFunction  // happy case
    )
  }

  def show(id: Int) = Action {
    var user: User= null
    listado.foreach(item =>
      if(item.id==id){
        user=item
      }
    )
    if (user==null){
      user= new User(0,"")
    }
    Ok(views.html.usuarioShow(user))
  }

}

