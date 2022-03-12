import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { cliente, coches, mecanicos } from 'src/app/interfaces/interfaces';
import { CocheService } from 'src/app/services/coche.service';

@Component({
  selector: 'app-factura',
  templateUrl: './factura.component.html',
  styleUrls: ['./factura.component.css']
})
export class FacturaComponent implements OnInit {

  coche: coches;
  cliente: cliente;
  mecanico: mecanicos;
  regForm: FormGroup;

  constructor(private cocheService: CocheService, private ruta: ActivatedRoute, private router: Router) { }

  ngOnInit(): void {
    this.obtenerFactura();

    this.regForm = new FormGroup({
      coste: new FormControl('', [Validators.required]),
    })

    if (this.coche.costeReparacion != '0') {
      var btnImprimir = document.getElementById('imprimir') as HTMLElement;

      btnImprimir.setAttribute('disabled', 'true');
    }
  }

  obtenerFactura(): void {
    let id = this.ruta.snapshot.paramMap.get('idCoche')
    this.cocheService.getPorId(id).subscribe(result => {
      if (result['estado'] != "error") {
        this.coche = result['coche'];
        this.cliente = result['coche'].cliente;
        this.mecanico = result['coche'].mecanico;
        console.log(result['coche'])
      }
    })
  }

  //Función dedicada imprimir la sección de la factura
  imprimir() {
    var newstr: HTMLElement = document.getElementById("impresion") as HTMLElement;
    var template: HTMLElement = document.getElementById("plantillaImpresion") as HTMLElement;
    var template2: HTMLElement = document.getElementById("plantillaImpresion2") as HTMLElement;
    var template3: HTMLElement = document.getElementById("plantillaImpresion3") as HTMLElement;
    var oldstr = document.body.innerHTML;

    //A la plantilla del coste hay que añadirle el precio que se haya establecido
    var pCosteAveria: HTMLElement = document.getElementById("pCoste") as HTMLElement;
    pCosteAveria.innerText = this.coche.costeReparacion + "€";

    //Establecer como cuerpo del documento la plantilla más el div de contenido a imprimir 
    document.body.innerHTML = template.innerHTML + newstr.innerHTML + template3.innerHTML + template2.innerHTML;
    window.print(); //Abrir el modal de impresión con el cuerpo establecido
    document.body.innerHTML = oldstr; //Es importante devolver el body como estaba 
    //window.history.back(); //Por cuestión de funcionalidad, mandar al mismo sitio y recargar
    //this.router.navigate([`/perfilUsuario/${this.cliente.id}`])
    this.router.navigate([`/taller`])
    //return false;
  }

  volver() {
    window.history.back();
  }

  actualizarCoste() {
    var inputCosteAveria = document.getElementById('costeAveria') as HTMLElement;
    var btnImprimir = document.getElementById('imprimir') as HTMLElement;

    btnImprimir.removeAttribute('disabled');
    inputCosteAveria.setAttribute('disabled', 'true');

    this.cocheService.actualizarCosteReparacion(this.coche.id, this.regForm.value.coste)
      .subscribe(result => {
        if (result['estado'] != "error") {
          console.log('Coste modificado correctamente')
        }
      })
  }
}
