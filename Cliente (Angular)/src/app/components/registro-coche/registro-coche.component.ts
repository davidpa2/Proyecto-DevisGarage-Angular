import { Component, OnInit, Input } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { CocheService } from 'src/app/services/coche.service';
import { ClienteService } from 'src/app/services/cliente.service';
import { cliente, coches, mecanicos } from 'src/app/interfaces/interfaces';
import { Router } from '@angular/router';
import { MecanicoService } from 'src/app/services/mecanico.service';

@Component({
  selector: 'app-registro-coche',
  templateUrl: './registro-coche.component.html',
  styleUrls: ['./registro-coche.component.css']
})
export class RegistroCocheComponent implements OnInit {

  idCliente: number;

  regForm: FormGroup;
  regForm2: FormGroup;
  fotoCoche: string;
  listaClientes: cliente[] = [];
  cliente: cliente;
  mecanico: mecanicos;
  //coche: Coche = new Coche();

  constructor(public service: CocheService, public serviceCliente: ClienteService,
    public serviceMecanico: MecanicoService, private route: Router) { }

  ngOnInit(): void {
    this.recuperarUsuarioLog();
    this.listarClientes();

    this.regForm = new FormGroup({
      marca: new FormControl('', [Validators.required]),
      modelo: new FormControl('', [Validators.required]),
      km: new FormControl('', [Validators.required]),
      cliente: new FormControl('', [Validators.required]),
      averias: new FormControl('', [Validators.required]),
      descripcionAveria: new FormControl('', [Validators.required]),
      fotoCoche: new FormControl('', [Validators.required]),
    })

    this.regForm2 = new FormGroup({
      nombre: new FormControl('', [Validators.required]),
      apellidos: new FormControl('', [Validators.required]),
      dni: new FormControl('', [Validators.required]),
      tlf: new FormControl('', [Validators.required]),
      gmail: new FormControl('', [Validators.required])
    })
  }

  /**
   * Registrar un nuevo cliente desde el formulario del componente
   */
  registerClient(): void {
    this.serviceCliente.addClient(this.regForm2.value.nombre, this.regForm2.value.apellidos,
      this.regForm2.value.dni, this.regForm2.value.tlf, this.regForm2.value.gmail)
      .subscribe(result => {
        if (result['estado'] != "error") {
          console.log('Cliente insertado correctamente')
        }
        this.idCliente = result['idCliente'];
      })
    window.location.href = "/registrarCoche";
  }

  /**
   * Registrar coche obteniendo los datos desde el formulario del componente
   */
  registerCar(): void {
    this.service.addCar(this.regForm.value.marca, this.regForm.value.modelo, this.regForm.value.km,
      this.regForm.value.averias, this.regForm.value.descripcionAveria, this.fotoCoche, "en cola", null,
      this.mecanico, this.cliente)
      .subscribe(result => {
        if (result['estado'] != "error") {
          console.log('Coche insertado correctamente')
        }
      })

    window.location.href = 'http://localhost:4200/taller';
    //this.route.navigate(['/taller']);
  }

  /**
   * Método usado para convertir la imagen a 64bits
   */
  guardarImagen() {
    let imgCoche: any = document.getElementById('imgCoche');
    imgCoche.classList.remove("d-none");

    const inputImg: any = document.getElementById('fotoCocheInput');

    //El file reader sirve para leer un blob o un file
    const reader = new FileReader();
    //Leer la imagen del input y cargarla en un ArrayBuffer. Desemboca un evento loadend y su atributo result
    //contiene un ArrayBuffer con los datos del archivo
    reader.readAsArrayBuffer(inputImg.files[0]);
    //Tras cargar la imagen la convertimos a base64
    reader.onload = (e: any) => {
      //btoa() convierte a base 64 desde una cadena de datos binarios
      this.fotoCoche = btoa(
        // Uint8Array es un array tipado de datos binarios
        //reduce() Aplica una función a un acumulador y a cada valor de un array (de izquierda a derecha) lo reduce a un único valor.
        new Uint8Array(e.target.result).reduce((data, byte) => data + String.fromCharCode(byte), '')
      );
    };
  }

  /**
   * Obtener todos los clientes para poder cargarlos en el select
   */
  listarClientes() {
    this.serviceCliente.getClientes().subscribe(result => {
      if (result['estado'] != "error") {
        result.listaClientes.forEach((c: cliente) => {
          this.listaClientes.push(c)
        });
      }
    })
  }

  /**
   * Mostrar u ocultar el formulario de registro de un nuevo cliente
   */
  showNewClientForm() {
    var btnAnnadirCoche = <HTMLElement>document.querySelector("#btnAnnadirCoche");
    var btnNuevoCliente = <HTMLElement>document.querySelector("#btnNuevoCliente");
    var formNewClient = <HTMLElement>document.getElementById("formNewClient");

    var classList = formNewClient.classList;

    classList.forEach(element => {
      if (element == "d-none") {
        formNewClient.classList.remove("d-none");
        formNewClient.classList.add("d-block");
        btnAnnadirCoche.classList.add("disabled");
        btnNuevoCliente.setAttribute("value", "Cancelar");

      } else if (element == "d-block") {
        formNewClient.classList.remove("d-block");
        formNewClient.classList.add("d-none");
        btnAnnadirCoche.classList.remove("disabled");
        btnNuevoCliente.setAttribute("value", "¿Nuevo cliente?");
      }
    });
  }

  /**
   * Al seleccionar un cliente del select, guardar en this.cliente el cliente seleccionado
   */
  recuperarClienteSeleccionado(): void {
    this.serviceCliente.findById(this.regForm.value.cliente).subscribe(result => {
      if (result['estado'] != "error") {
        this.cliente = result['cliente'];
        console.log(result['cliente'])
      }
    })
  }

  /**
   * Para registrar un coche es necesario saber qué mecánico ha iniciado sesión
   */
  recuperarUsuarioLog() {
    this.mecanico = JSON.parse(localStorage.getItem("mecanicoAutentificado"))
  }
}