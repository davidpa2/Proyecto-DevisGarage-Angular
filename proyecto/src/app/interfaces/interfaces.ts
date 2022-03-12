export interface coches {
    id: number,
    marca: string,
    modelo: string,
    km: string,
    averias: number,
    descripcionAveria: string,
    estado: string,
    fotoCoche: string,
    fechaReparacion: string,
    costeReparacion: string,
    cliente_id: cliente,
    mecanico_id: mecanicos
}

export interface mecanicos {
    id: number,
    nombre: string,
    apellidos: string,
    dni: string,
    tlf: string,
    rol: string,
    email: string,
    password: string
}

export interface cliente {
    id: number,
    nombre: string,
    apellidos: string,
    dni: string,
    tlf: string,
    gmail: string,
}

export interface TokenJWT {
    jwt: any
}