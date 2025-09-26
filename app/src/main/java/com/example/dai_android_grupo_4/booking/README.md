# Módulo de Reservas (Booking)

Este módulo implementa la funcionalidad del punto 5 de la especificación: **Reservas**.

## Estructura del Módulo

```
booking/
├── di/                          # Inyección de dependencias
│   └── BookingModule.java
├── model/                       # Modelos de datos
│   └── Booking.java
├── repository/                  # Repositorio de datos
│   ├── BookingRepository.java
│   └── BookingRepositoryImpl.java
├── service/                     # Servicios de negocio
│   ├── BookingService.java
│   └── BookingServiceImpl.java
└── ui/                         # Interfaz de usuario
    ├── BookingActivity.java    # Activity principal
    ├── adapter/                # Adaptadores para RecyclerView
    │   └── BookingAdapter.java
    ├── fragments/              # Fragments
    │   ├── BookingListFragment.java
    │   ├── BookingDetailFragment.java
    │   └── CreateBookingFragment.java
    └── viewmodel/              # ViewModels
        └── BookingViewModel.java
```

## Funcionalidades Implementadas

### 1. **BookingActivity**
- Activity principal que maneja la navegación entre fragments
- Implementa navegación inferior (Bottom Navigation)
- Gestiona el stack de fragments

### 2. **BookingListFragment**
- Muestra la lista de reservas del usuario
- Implementa filtros por estado y fecha
- Permite cancelar reservas
- Navega al detalle de la reserva al hacer click

### 3. **BookingDetailFragment**
- Muestra información detallada de una reserva específica
- Permite cancelar la reserva (si es posible)
- Incluye botón "Cómo llegar" para abrir Google Maps
- Muestra estado, instructor, fecha, hora y ubicación

### 4. **CreateBookingFragment**
- Permite crear nuevas reservas
- Selectores para clase, instructor, fecha y horario
- Validación de datos antes de crear la reserva

### 5. **BookingAdapter**
- Adaptador para el RecyclerView de reservas
- Maneja diferentes estados de reserva (CONFIRMED, CANCELED, COMPLETED, EXPIRED)
- Implementa click listeners para acciones

## Modelo de Datos

### Booking
```java
- String id
- String className
- String instructor
- String date
- String time
- String location
- String status (CONFIRMED, CANCELED, COMPLETED, EXPIRED)
- String duration
- int capacity
- int currentBookings
- String description
- Date createdAt
- Date updatedAt
```

## Estados de Reserva

- **CONFIRMED**: Reserva confirmada y activa
- **CANCELED**: Reserva cancelada por el usuario
- **COMPLETED**: Clase completada
- **EXPIRED**: Reserva expirada

## Navegación

La navegación se realiza a través de:
1. **Bottom Navigation**: Para cambiar entre secciones principales
2. **Fragment Navigation**: Para navegar entre detalles y listas
3. **Back Stack**: Para mantener el historial de navegación

## Integración con Hilt

El módulo está completamente integrado con Hilt para inyección de dependencias:
- `BookingModule`: Configura las dependencias del módulo
- `@HiltViewModel`: Para el ViewModel
- `@AndroidEntryPoint`: Para Activities y Fragments

## Próximos Pasos

1. **Integración con API**: Conectar con el sistema interno de RitmoFit
2. **Validaciones**: Implementar validaciones de cupo y horarios
3. **Notificaciones**: Integrar con el sistema de notificaciones push
4. **Filtros Avanzados**: Implementar filtros por sede y disciplina
5. **Historial**: Crear fragment para historial de asistencias

## Recursos Necesarios

- Iconos para estados y acciones
- Colores para diferentes estados
- Layouts responsivos
- Estilos Material Design

## Testing

- Unit tests para ViewModel y Repository
- UI tests para fragments
- Integration tests para flujos completos
