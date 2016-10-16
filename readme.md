#Gastronomica 

##Informacion basica

* Dominio: gastronomica.mobi
* Subdominio: api
* Access Token: 221c7b93a8261314688a39bac6d8cfe6

Todas las rutas necesitan de un access token para poder acceder a ellas. El access token en estos momentos es 221c7b93a8261314688a39bac6d8cfe6  , se pasa como parametro en la url, por ejemplo: 

```
#!

http://api.gastronomica.mobi/v1/restaurante?access_token=221c7b93a8261314688a39bac6d8cfe6

```

##Urls

###Restaurantes

####Obtener todos los restaurantes 

Metodo GET

```
#!
http://api.gastronomica.mobi/v1/restaurante
```

####Obtener un restaurante

Metodo GET
Variable id, posterior a "restaurante/", se coloca el numero de la id del restaurante que se quiere encontrar.
```
#!
http://api.gastronomica.mobi/v1/restaurante/:id
```

####Cerca de mi
Metodo GET
Se pasa el valor de latitude y longitude, buscara los restaurantes cerca de ese punto en un area de 10 km a la redonda

```
#!
http://api.gastronomica.mobi/v1/restaurantes?latitude=10.9904304&longitude=-63.830483,15
```

###Eventos

####Obtener todos los eventos cercanos a ti
Metodo GET
Se pasa el valor de latitude y longitude, buscara los restaurantes cerca de ese punto en un area de 10 km a la redonda
```
#!

http://api.gastronomica.mobi/v1/eventos?latitude=10.9904304&longitude=-63.830483,15
```

####Obtener un evento
Metodo GET
se pasa el id por url

```
#!

http://api.gastronomica.mobi/v1/eventos/:id
```

###Usuarios

####Autenticacion de usuario
Metodo POST
variables:
 email: "cuenta@hotmail.com"
 password: "12345678"

```
#!
http://api.gastronomica.mobi/v1/sesion?email=cuenta@hotmail.com&password=12345678
```

####Registro de usuario
Metodo POST
Ejemplo del JSON a enviar

```
#!javascript
{
    "client":{
        "first_name": "Name",
        "last_name": "LastName",
        "email": "foo@email.com",
        "roll": 1,
        "level":1,
        "password": "12345678"
    }
}
```

```
#!
http://api.gastronomica.mobi/v1/registro
```

####Cambiar password

Existen dos url para cambiar el password, una url para enviar el token al correo del usuario y otra para hacer el cambio del password del usuario, comprobando que sea el correo y el token

#####Enviar token y correo 
Metodo POST
El unico parametro a enviar es el email que solicite el cambio
```
#!
{
    "email":"davidriverome@gmail.com"
}

```

URL
```
#!
api.gastronomica.mobi/v1/resetear_password
```

#####Cambiar el password

Metodo POST
Ejemplo del JSON a enviar:

```
#!javascript
{
    "token":"n17Rr9uj0W61m7T6h_5cZg",
    "password":"nuevo_password"
}
```
token vendria siendo el del usuario que solicito el cambio de password, y password es el nuevo password

URL
```
#!
api.gastronomica.mobi/v1/cambiar_password
```





###Publicaciones

###Ver todas las publicaciones
Metodo GET


```
#!
http://api.gastronomica.mobi/v1/publicaciones

```

####Ver una publicacion
Metodo GET
Variable id, posterior a "publicaciones/", se coloca el numero de la id de la publicacion que se quiere encontrar.

```
#!

http://api.gastronomica.mobi/v1/publicaciones/:id
```

###Colaboradores

####Ver todos los colaboradores

```
#!

http://api.gastronomica.mobi/v1/colaboradores
```

###Resena 

####Crear Resena

Se le debe pasar la id del restaurante al que se le hara la resena, y la id del usuario que registro la resena


```
#!

http://api.gastronomica.mobi/v1/crear_resena
```

```
#!javascript
{
    "review":{
        "re_title":"Jamas volveria",
        "re_content": "No vayan muchachos",
        "re_start": 5,
        "client_id":1,
        "restaurant_id": 2
    }
}

```

####Resenas por id de restaurante

Se le debe pasar la id del restaurante.


```
#!

http://api.gastronomica.mobi/v1/resenas/:id
```