import { Component, OnInit } from '@angular/core';
import { coches, mecanicos } from 'src/app/interfaces/interfaces';
import { CocheService } from 'src/app/services/coche.service';

@Component({
  selector: 'app-reparaciones-mecanico',
  templateUrl: './reparaciones-mecanico.component.html',
  styleUrls: ['./reparaciones-mecanico.component.css']
})
export class ReparacionesMecanicoComponent implements OnInit {

  listaFacturas: coches[] = [];
  mecanico: mecanicos;

  constructor(private cocheService: CocheService) { }

  ngOnInit(): void {
    this.recuperarUsuarioLog();
  }

  obtenerFacturas(id) {
    this.cocheService.getFacturas(id).subscribe(result => {
      if (result['estado'] != "error") {
        result.listaFacturas.forEach((c: coches) => {
          this.listaFacturas.push(c)
        });      
      }
    })
  }

  recuperarUsuarioLog() {
    this.mecanico = JSON.parse(localStorage.getItem("mecanicoAutentificado"))
    this.obtenerFacturas(this.mecanico.id);
  }
}
