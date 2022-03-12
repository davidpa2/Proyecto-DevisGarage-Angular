import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { MecanicoService } from 'src/app/services/mecanico.service';

@Component({
  selector: 'app-bienvenida',
  templateUrl: './bienvenida.component.html',
  styleUrls: ['./bienvenida.component.css']
})
export class BienvenidaComponent implements OnInit {

  regForm: FormGroup;

  constructor(private mecanicoService: MecanicoService, private router: Router) { }

  ngOnInit(): void {
    this.regForm = new FormGroup({
      email: new FormControl('', [Validators.required]),
      password: new FormControl('', [Validators.required]),
    })
  }

  singIn() {
    console.log(this.regForm.value.email);
    console.log(this.regForm.value.password);
    
    this.mecanicoService.autenticaUsuario(this.regForm.value.email, this.regForm.value.password).subscribe(data => {
      console.log(data);
      if (data.jwt) {
        this.mecanicoService.JWT = data.jwt;
        this.mecanicoService.emitirNuevoCambioEnUsuarioAutenticado();
        this.router.navigate(['/taller']);
      } else {
        alert("Error: Usuario y/o contraseña incorrectos");
      }
    })
  }
}
