package ru.verkhovin.cloudchats.api.error

//import io.micronaut.context.annotation.Requires
//import io.micronaut.http.HttpRequest
//import io.micronaut.http.HttpResponse
//import io.micronaut.http.server.exceptions.ExceptionHandler
//import ru.verkhovin.cloudchats.exception.EntityNotFoundException
//import javax.inject.Singleton
//
//@Singleton
//@Requires(classes = [EntityNotFoundException::class])
//class EntityNotFoundExceptionHandler : ExceptionHandler<EntityNotFoundException, HttpResponse<ErrorResponse>> {
//  override fun handle(request: HttpRequest<*>, exception: EntityNotFoundException): HttpResponse<ErrorResponse> {
//    return HttpResponse.notFound(ErrorResponse("${exception.entityName} was not found", ErrorCode.NOT_FOUND))
//  }
//
//}
