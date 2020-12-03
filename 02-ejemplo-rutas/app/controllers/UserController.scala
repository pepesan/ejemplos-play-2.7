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
  /**
    * Create an Action to render an HTML page with a welcome message.
    * The configuration in the `routes` file means that this method
    * will be called when the application receives a `GET` request with
    * a path of `/`.
    */
  def index = Action {
    // Presenta un dato
    Ok(views.html.usuario.Index("Página de Usuario"))
  }
  def list = Action {
    // Presenta un listado

    Ok(views.html.usuario.List(listado))
  }

  // objeto del formulario a presentar
  val form = Form(
    // mapeo de campos del formulario
    mapping(
      //nombre de campo y criterios de validación
      "name" -> nonEmptyText,
      "iden" -> number(min = 10, max = 100)
      // Tipo de dato a usar dentro del formulario
    )(UserForm.apply)(UserForm.unapply)
  )
  private val saveUrl = routes.UserController.save()
  def addForm = Action { implicit request =>
    // pass an unpopulated form to the template
    Ok(views.html.usuario.Form(form, saveUrl))
  }
  def save = Action { implicit request =>
    val errorFunction = { formWithErrors: Form[UserForm] =>
      // this is the bad case, where the form had validation errors.
      // show the user the form again, with the errors highlighted.
      BadRequest(views.html.usuario.Form(formWithErrors, saveUrl))
    }

    val successFunction = { data: UserForm =>
      // this is the SUCCESS case, where the form was successfully parsed as a BlogPost
      val user = new User(data.iden,data.name)
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
    var user: User= new User(0,"")
    // por cada elemento del listado ejecuta una función
    listado.foreach({
      // la función tiene un parámetro de entrada que el item elegido
      item =>
        //código de la función que quieres hacer por cada item
      {
        if (item.id == id) {
          user = item
        }
      }
    }
    )

    //listado.foreach(item => if (item.id == id) user = item)

    Ok(views.html.usuario.Show(user))
  }

}

