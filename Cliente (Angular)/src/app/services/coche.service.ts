import { Observable, throwError } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { cliente, mecanicos } from '../interfaces/interfaces';

@Injectable({
  providedIn: 'root'
})
export class CocheService {

  private url: string = "http://localhost:8080";

  constructor(private http: HttpClient) { }

  get(): Observable<any> {
    //http.get() manda una solicitud http y devuelve un objeto Observable que emite los datos solicitados
    return this.http.get<any>(this.url + '/listartodos');
  }

  getFacturas(id): Observable<any> {
    return this.http.get<any>(`${this.url}/facturasMecanico/${id}`);
  }

  getPorId(id): Observable<any> {
    return this.http.get<any>(`${this.url}/findById/${id}`);
  }

  cochesEnCola(): Observable<any> {
    //http.get() manda una solicitud http y devuelve un objeto Observable que emite los datos solicitados
    return this.http.get<any>(this.url + '/cochesEnCola');
  }

  cochesEnReparacion(): Observable<any> {
    //http.get() manda una solicitud http y devuelve un objeto Observable que emite los datos solicitados
    return this.http.get<any>(this.url + '/cochesEnReparacion');
  }

  cochesCompletados(): Observable<any> {
    //http.get() manda una solicitud http y devuelve un objeto Observable que emite los datos solicitados
    return this.http.get<any>(this.url + '/cochesCompletados');
  }

  addCar(marca: string, modelo: string, km: string, averias: number, descripcionAveria: string, fotoCoche: string,
    estado: string, fechaReparacion: string, mecanico: mecanicos, cliente: cliente): Observable<any> {
    let headers = new HttpHeaders().set('Content-Type', 'application/json');

    let coche = {
      marca, modelo, km, averias, descripcionAveria, fotoCoche, estado, fechaReparacion, mecanico, cliente
    }

    let jsonCoches = JSON.stringify({
      marca: coche.marca,
      modelo: coche.modelo,
      km: coche.km,
      averias: coche.averias,
      descripcionAveria: coche.descripcionAveria,
      fotoCoche: coche.fotoCoche,
      estado: coche.estado,
      fechaReparacion: coche.fechaReparacion,
      mecanico: coche.mecanico,
      cliente: coche.cliente,
    })
    return this.http.post<any>(this.url + '/registroCoche', jsonCoches, { headers: headers })
  }

  actualizarCosteReparacion(id: number, coste: String): Observable<any> {
    return this.http.get<any>(`${this.url}/actualizarCosteReparacion/${id}/${coste}`);
  }
}