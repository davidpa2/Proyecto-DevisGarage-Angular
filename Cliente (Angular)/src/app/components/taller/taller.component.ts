import { Component, OnInit } from '@angular/core';
import { CocheService } from 'src/app/services/coche.service';
import { coches, mecanicos } from 'src/app/interfaces/interfaces';
import axios from 'axios';
import { Directive, ElementRef, HostListener } from '@angular/core';

@Component({
  selector: 'app-taller',
  templateUrl: './taller.component.html',
  styleUrls: ['./taller.component.css']
})
export class TallerComponent implements OnInit {

  mecanico: mecanicos;
  listaCoches: coches[] = [];
  listaCochesEnCola: coches[] = [];
  listaCochesEnReparacion: coches[] = [];
  listaCochesCompletados: coches[] = [];

  constructor(private cocheService: CocheService) { }

  ngOnInit(): void {
    //this.obtenerCoches();
    this.cochesEnCola();
    this.cochesEnReparacion();
    this.cochesCompletados();
  }

  /**
   * Recoger el evento dragstart y ejecutar la función asíncrona para actualizar el estado del coche arrastrado
   * @param target 
   */
  @HostListener('dragleave', ["$event.target"]) async drop(target) {
    let id = target.id;

    axios.get(`http://localhost:8080/actualizarEstadoCoche/${id}`).then(function(response) {
      console.log(response)
    })
    
   window.location.href = "/taller";
  }

  obtenerCoches() {
    this.cocheService.get().subscribe(result => {
      if (result['estado'] != "error") {
        result.listaCoches.forEach((c: coches) => {
          this.listaCoches.push(c)
        });
      }
    })
  }

  cochesEnCola() {
    this.cocheService.cochesEnCola().subscribe(result => {
      if (result['estado'] != "error") {
        result.listaCochesEnCola.forEach((c: coches) => {
          this.listaCochesEnCola.push(c)
        });
      }
    })
  }

  cochesEnReparacion() {
    this.cocheService.cochesEnReparacion().subscribe(result => {
      if (result['estado'] != "error") {
        result.listaCochesEnReparacion.forEach((c: coches) => {
          this.listaCochesEnReparacion.push(c)
        });
      }
    })
  }
  
  cochesCompletados() {
    this.cocheService.cochesCompletados().subscribe(result => {
      if (result['estado'] != "error") {
        result.listaCochesCompletados.forEach((c: coches) => {
          this.listaCochesCompletados.push(c)
        });
      }
    })
  }

  recuperarUsuarioLog(){
    this.mecanico = JSON.parse(localStorage.getItem("mecanicoAutentificado"))
  }
}