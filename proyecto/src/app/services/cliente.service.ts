import { Observable, throwError } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ClienteService {

  private url: string = "http://localhost:8080";

  constructor(private http: HttpClient) { }

  getClientes(): Observable<any> {
    //http.get() manda una solicitud http y devuelve un objeto Observable que emite los datos solicitados
    return this.http.get<any>(this.url + '/listarClientes');
  }

  addClient(nombre: string, apellidos: string, dni: string, tlf: string, gmail: string): Observable<any> {
    let headers = new HttpHeaders().set('Content-Type', 'application/json');

    let cliente = {
      nombre, apellidos, dni, tlf, gmail
    }

    let jsonClientes = JSON.stringify({
      nombre: cliente.nombre,
      apellidos: cliente.apellidos,
      dni: cliente.dni,
      tlf: cliente.tlf,
      gmail: cliente.gmail
    })
    return this.http.post<any>(this.url + '/registroCliente', jsonClientes, { headers: headers })
  }

  findById(id) {
    return this.http.get<any>(`${this.url}/clientePorId/${id}`)
  }
}
