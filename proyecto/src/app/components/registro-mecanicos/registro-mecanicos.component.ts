import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MecanicoService } from 'src/app/services/mecanico.service';
import { Router } from '@angular/router';
import { mecanicos } from 'src/app/interfaces/interfaces';

@Component({
  selector: 'app-registro-mecanicos',
  templateUrl: './registro-mecanicos.component.html',
  styleUrls: ['./registro-mecanicos.component.css']
})
export class RegistroMecanicosComponent implements OnInit {

  listaMecanicos: mecanicos[] = [];

  regForm: FormGroup;

  constructor(public serviceMecanico: MecanicoService, private route: Router) { }

  ngOnInit(): void {
    this.regForm = new FormGroup({
      nombre: new FormControl('', [Validators.required]),
      apellidos: new FormControl('', [Validators.required]),
      dni: new FormControl('', [Validators.required]),
      tlf: new FormControl('', [Validators.required]),
      email: new FormControl('', [Validators.required]),
      password: new FormControl('', [Validators.required]),
    })
  }

  /**
   * Registrar un nuevo cliente desde el formulario del componente
   */
   registrarMecanico(): void {
    this.serviceMecanico.addMecanico(this.regForm.value.nombre, this.regForm.value.apellidos,
      this.regForm.value.dni, this.regForm.value.tlf, "empleado", this.regForm.value.email, this.regForm.value.password)
      .subscribe(result => {
        if (result['estado'] != "error") {
          console.log('Mecanico contratado correctamente')
        }
      })
      
    //this.route.navigate(['/listaMecanicos']);
    window.location.href = 'http://localhost:4200/listaMecanicos';
  }
}
