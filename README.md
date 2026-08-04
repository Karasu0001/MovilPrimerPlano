# Dialitech — Android App

Aplicación nativa Android para pacientes en diálisis, desarrollada en **Kotlin + Jetpack Compose + Material 3**.

## Requisitos

- Android Studio Hedgehog (2023.1.1) o superior
- JDK 17
- Gradle 8.9
- Android SDK 35 (Target) / 26 (Min SDK)

## Cómo compilar

```bash
# En la raíz del proyecto
./gradlew assembleDebug
```

El APK se genera en `app/build/outputs/apk/debug/app-debug.apk`.

## Cómo ejecutar

Abrir el proyecto en Android Studio y presionar Run ▶️, o usar:

```bash
./gradlew installDebug
```

## Estructura del proyecto

```
app/src/main/java/com/healthsync/app/
├── HealthSyncApp.kt              # Application class
├── MainActivity.kt               # Single Activity con NavHost
├── navigation/
│   ├── NavGraph.kt               # Configuración de rutas
│   ├── Routes.kt                 # Sealed class de rutas
│   └── PlaceholderScreen.kt      # Stub (ya no usado, mantenido por referencia)
├── ui/
│   ├── theme/
│   │   ├── Color.kt              # Tokens de color (traducidos del proyecto web)
│   │   ├── Type.kt               # Tipografía Inter
│   │   ├── Shape.kt              # Radios
│   │   └── Theme.kt              # HealthSyncTheme (Material 3)
│   ├── components/
│   │   ├── BrandLogo.kt          # Logo de marca reutilizable
│   │   ├── PrimaryButton.kt      # Botón primario/outline
│   │   ├── AppCard.kt            # Tarjeta con sombra
│   │   ├── WearableFrame.kt      # Carátula de smartwatch simulada
│   │   └── BottomNavBar.kt       # Barra de navegación inferior
│   └── screens/
│       ├── auth/
│       │   ├── LoginScreen.kt          # Inicio de sesión
│       │   ├── RegisterScreen.kt       # Registro de cuenta
│       │   └── ForgotPasswordScreen.kt # Recuperación de contraseña
│       ├── onboarding/
│       │   └── WelcomeScreen.kt  # Pantalla de bienvenida
│       ├── dashboard/
│       │   └── DashboardScreen.kt # Pantalla principal post-login
│       ├── pairing/
│       │   ├── code/             # Vinculación por código
│       │   │   ├── CodePairingFlowScreen.kt      # Contenedor del flujo
│       │   │   ├── BuscandoDispositivoScreen.kt   # Búsqueda Bluetooth
│       │   │   ├── ConfirmarEmparejamientoScreen.kt # Código 458 921
│       │   │   ├── SincronizandoDispositivoScreen.kt # Progreso 0→100%
│       │   │   ├── VinculacionExitosaScreen.kt    # Check verde + metadata
│       │   │   ├── VinculacionCanceladaScreen.kt  # Error + reintentar
│       │   │   ├── WearableConfirmarCodigoContent.kt   # Watch: código
│       │   │   ├── WearableVinculandoContent.kt        # Watch: vinculando
│       │   │   ├── WearableSincronizarAhoraContent.kt  # Watch: sync
│       │   │   ├── WearableDispositivoVinculadoContent.kt # Watch: éxito
│       │   │   └── WearableSolicitudRechazadaContent.kt  # Watch: error
│       │   └── qr/               # Vinculación por QR
│       │       ├── QrPairingFlowScreen.kt        # Contenedor del flujo QR
│       │       ├── CameraPermissionScreen.kt     # Permiso de cámara
│       │       ├── QrScanScreen.kt               # Escaneo de QR
│       │       ├── QrConfirmScreen.kt            # Confirmación de dispositivo
│       │       ├── QrSyncScreen.kt               # Sincronización
│       │       ├── QrSuccessScreen.kt            # Éxito
│       │       ├── QrCancelledScreen.kt          # Cancelado
│       │       └── WearableQrContent.kt          # Watch: QR + estados
│       └── wearable/vitaltrack/  # Monitoreo VitalTrack
│           └── VitalTrackScreen.kt # Signos vitales simulados en vivo
└── viewmodel/
    ├── LoginViewModel.kt         # Estado del formulario de login
    ├── RegisterViewModel.kt      # Estado del formulario de registro
    ├── ForgotPasswordViewModel.kt # Estado de recuperación de contraseña
    ├── WelcomeViewModel.kt       # Estado de la pantalla de bienvenida
    ├── CodePairingViewModel.kt   # Máquina de estados del flujo de código
    └── QrPairingViewModel.kt     # Máquina de estados del flujo QR
```

## Pantallas implementadas

### Auth
- **LoginScreen**: formulario con email/password, toggle de visibilidad, checkbox "Recordar", validación local, botones sociales Google/Apple (stubs).
- **RegisterScreen**: formulario con nombre, correo, contraseña y confirmación, validación local via `RegisterViewModel`.
- **ForgotPasswordScreen**: campo de correo, envío de enlace de recuperación simulado, estado de confirmación visual.

### Onboarding
- **WelcomeScreen**: hero con gradiente, foto de paciente, CTA "Comenzar", link "Iniciar sesión", badges de confianza.

### Dashboard
- **DashboardScreen**: pantalla principal post-login con BottomNavBar, resumen de dispositivo vinculado, accesos rápidos a Actividad/Signos Vitales/Perfil/Dispositivos, y tabla de resumen de hoy (placeholders).

### Vinculación por código (10 pantallas)
Flujo completo controlado por `CodePairingViewModel` con máquina de estados `PairingStep`:

1. `searching` → BuscandoDispositivo + BottomNav
2. `confirming` → ConfirmarEmparejamiento (teléfono) + WearableConfirmarCodigo (watch)
3. `syncing` → SincronizandoDispositivo (progreso animado 0→100%) + WearableVinculando + WearableSincronizarAhora
4. `success` → VinculacionExitosa + WearableDispositivoVinculado
5. `cancelled` → VinculacionCancelada + WearableSolicitudRechazada

En desktop, teléfono y wearable se muestran lado a lado; el wearable se renderiza dentro de `WearableFrame` (280x280dp, fondo oscuro, bisel negro).

### Vinculación por QR (8 pantallas)
Flujo controlado por `QrPairingViewModel` con máquina de estados `QrPairingStep`:

1. `camera_request` → CameraPermissionScreen (solicitud de permiso de cámara)
2. `scanning` → QrScanScreen (escaneo) + WearableShowQrContent (watch muestra QR con ZXing)
3. `confirming` → QrConfirmScreen (confirmación) + WearableQrConfirmContent (watch acepta/rechaza)
4. `syncing` → QrSyncScreen (progreso 0→100%) + WearableQrSyncingContent
5. `success` → QrSuccessScreen + WearableQrSuccessContent
6. `cancelled` → QrCancelledScreen + WearableQrCancelledContent

Usa CameraX + ML Kit Barcode Scanning para lectura de QR y ZXing para generación en el wearable simulado.

### VitalTrack
- **VitalTrackScreen**: monitoreo continuo de signos vitales simulados (frecuencia cardíaca 72 lpm, SpO2 98%, PA 120/80, temperatura 36.5°C) en formato wearable dentro de `WearableFrame`.

## Pendiente de integración real

| Funcionalidad | Estado | TODO |
|--------------|--------|------|
| Bluetooth LE (escaneo) | Simulado | Reemplazar en `CodePairingViewModel.onConnect()` |
| Health Connect API | No iniciado | Vincular datos biométricos reales |
| Autenticación real (REST/OAuth) | Simulado | Reemplazar en `LoginViewModel.login()` / `RegisterViewModel.register()` |
| Cámara + ML Kit QR | Implementado | Integrar analizador de cámara real (actualmente simulado) |
| ZXing generación de QR | Implementado | Generación funcional en `WearableShowQrContent` |
| Pantallas VitalTrack | Implementado | Monitoreo continuo simulado |
| Dashboard principal | Implementado | Pantalla principal del paciente completa |
| Registro / Forgot Password | Implementado | Formularios con validación local |
| Firebase / backend | No iniciado | Persistencia y sincronización cloud |
| Notificaciones push | No iniciado | Alertas de desconexión, métricas críticas |

## Convenciones

- 100% Kotlin, sin Java
- MVVM: Screen (Composable) + ViewModel (StateFlow/mutableStateOf)
- Navigation Compose para ruteo
- Material 3 + tokens de color definidos en `Color.kt`
- Inter font (TODO: agregar a `res/font/`)
