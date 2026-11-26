# Proyecto Monitoreo de Sonido

Aplicación de consola para una empresa de monitoreo de ruido: registra proyectos de medición, los lleva por los
estados Recibido, En Medición, Alerta o Normal comparando el dato medido con el umbral de su variable
(LAN01, LAN02, LAN03) y guarda todo en un archivo JSON

## Tecnologías
- Java [versión que usas]
- Maven
- Persistencia en archivo JSON

## Estructura del proyecto

```
src/main/java
├── controller    → lógica del programa (ProjectController, ProjectList, DoublyLinkedList)
├── model         → clases de datos (Project, Node, NoiseVariable, ProjectStatus)
├── persistence   → guardado y carga de datos (ProjectRepository)
├── view          → interfaz de consola (ConsoleView)
└── Main.java     → punto de entrada
```

## Conceptos practicados
- Programación orientada a objetos
- Lista doblemente ligada implementada desde cero
- Arquitectura MVC
- Persistencia de datos en JSON

## Cómo ejecutarlo
1. Clona el repositorio:
   ```bash
   git clone https://github.com/MzAngel0/Proyecto_Monitoreo_De_-Sonido.git
   ```
2. Abre la carpeta en tu IDE (IntelliJ o VS Code).
3. Ejecuta la clase `Main.java`.

## Autor
Ángel - [MzAngel0](https://github.com/MzAngel0)
