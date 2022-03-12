import { Component, OnInit, Input } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { coches, mecanicos } from 'src/app/interfaces/interfaces';
import { CocheService } from 'src/app/services/coche.service';
import { MecanicoService } from 'src/app/services/mecanico.service';

@Component({
  selector: 'app-perfil-usuario',
  templateUrl: './perfil-usuario.component.html',
  styleUrls: ['./perfil-usuario.component.css']
})
export class PerfilUsuarioComponent implements OnInit {

  id: number
  nombre: String
  apellidos: String
  dni: String
  tlf: String
  email: String

  listaFacturas: coches[] = [];

  regForm: FormGroup;
  /*nombres:string[]=["Paco","Juan","Valle","Fran","Sabri","David","DaniPA"];
  apellidos:string[]=["Pérez","Gallardo","Lópes","Fenan","Ojea","Pajeou","Safra"];*/

  constructor(public serviceMecanico: MecanicoService, private cocheService: CocheService, private ruta: ActivatedRoute) { }

  ngOnInit(): void {
    this.mostrarMecanico();

    this.regForm = new FormGroup({
      id: new FormControl('', [Validators.required]),
      nombre: new FormControl('', [Validators.required]),
      apellidos: new FormControl('', [Validators.required]),
      dni: new FormControl('', [Validators.required]),
      tlf: new FormControl('', [Validators.required]),
      email: new FormControl('', [Validators.required])
    })

    
  }

  mostrarMecanico(): void {
    let id = this.ruta.snapshot.paramMap.get('id')
    this.serviceMecanico.buscarMecanicoId(id).subscribe(result => {
      if (result['estado'] != "error") {
        result.listaMecanicos.forEach((m: mecanicos) => {
          this.id = m.id
          this.nombre = m.nombre
          this.apellidos = m.apellidos
          this.dni = m.dni
          this.tlf = m.tlf
          this.email = m.email
          this.obtenerFacturas(m.id);
        });
      }
    })
  }

  /**
   * Registrar un nuevo cliente desde el formulario del componente
   */
  modificarMecanico(): void {
    this.serviceMecanico.modificarMecanico(this.id, this.regForm.value.nombre, this.regForm.value.apellidos,
      this.regForm.value.dni, this.regForm.value.tlf)
      .subscribe(result => {
        if (result['estado'] != "error") {
          console.log('Mecanico modificado correctamente')
        }
      })

    //this.route.navigate(['/listaMecanicos']);
    //window.location.reload();
    /*window.location.href = `http://localhost:4200/perfilUsuario/${this.id}`;*/
    window.history.back();
  }

  despedirMecanico(): void {
    this.serviceMecanico.despedirMecanico(this.id)
      .subscribe(result => {
        if (result['estado'] != "error") {
          console.log('Mecanico despedido correctamente')
        }
      })
    window.history.back();
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
}
