🇦🇷 Título Tentativo:
“Operación Última Guardia”
🎮 Concepto General del Juego

Operación Última Guardia es un juego 2D top–down donde controlás a un soldado de élite perteneciente a la A.A.A. (Alianza Antiapocalipsis Argentina), un organismo secreto creado para combatir una infección zombie que se está propagando por todo el país.

Tu misión es infiltrarte en varios puntos críticos del territorio argentino para destruir los “Núcleos de Infección”, estructuras biotecnológicas que generan oleadas de zombies. Cada nivel se ubica en una locación icónica del país, adaptada al gameplay.

🧠 Contexto Narrativo

En 2028, una farmacéutica internacional ilegalmente instala laboratorios clandestinos en distintas zonas de Argentina para probar un “suero de mejora genética”. El experimento sale mal y convierte a los infectados en versiones mutadas, agresivas y no-muertas.

La A.A.A., organización militar encubierta, recluta a Dante Varela, un soldado altamente entrenado, para liderar una operación de contención: destruir los Núcleos que esparcen la infección.

🎮 Mecánicas Principales (Top–Down 2D)

Pensadas para implementarlas fácilmente en FXGL.

1. Movimiento y Combate

Movimiento en 8 direcciones (WASD).

El mouse apunta / dispara.

Arma inicial: fusil semiautomático.

Munición limitada → pickups.

Golpe cuerpo a cuerpo (ataque rápido).

2. Sistema de Spawners (Núcleos de Infección)

Cada nivel tiene entre 1 y 3 Núcleos.

De ellos salen zombies cada X segundos.

Al destruir un Núcleo, la cantidad de zombies disminuye.

Mini–idea estética:

Los Núcleos pueden ser:

Tanques biológicos rotos

Biomasa mutante pulsante

Torres mecánico–orgánicas con tubos verdes

FXGL permite animaciones simples y particulas para hacerlo más “vivo”.

3. Zombies y Variantes

Para mantenerlo simple pero interesante:

Zombi Común

Lento, básico.

Corredor

Más rápido, poca vida.

Gordo Mutado

Explota al morir → daño de área.

(Podés agregar más en el futuro.)

4. Objetivo por Nivel

Llegar al área infectada.

Sobrevivir oleadas mientras destruis los Núcleos.

Huir hacia el punto de extracción.

5. HUD / UI

Vida

Munición

Minimapa simple con la posición de spawners

Barra de progreso del Núcleo al destruirlo

🗺️ Propuesta de 5 Niveles Ambientados en Argentina

Cada nivel tiene una estética particular y una mecánica especial.

Nivel 1 — Barrio de Once, CABA

Objetivo: tutorial + destruir un único Núcleo.
Características:

Calles estrechas, autos abandonados.

Enseña movimiento, disparo y HUD.

Primeros zombies lentos.

Nivel 2 — Ruta Nacional 9 (a la altura de Rosario)

Objetivo: destruir dos Núcleos a lo largo de un tramo de ruta.
Características:

Camino lineal pero largo.

Camiones volcados, estaciones de servicio.

Aparición de corredores.

Nivel 3 — Estación de Tren de Córdoba

Objetivo: sobrevivir mientras se destruye un Núcleo dentro de la estación.
Características:

Trenes detenidos que sirven como cobertura.

Oleadas más densas.

Introducción del Gordo Mutado.

Nivel 4 — Mina de San Julián (Santa Cruz)

Objetivo: bajar al área subterránea y destruir tres Núcleos.
Características:

Túneles laberínticos.

Visibilidad reducida (linterna tipo fog-of-war).

Zombies más resistentes.

Nivel 5 — Laboratorio Principal (Ushuaia)

Objetivo Final: destruir el Núcleo Maestro.
Características:

Ambiente científico / tecnológico.

Oleadas masivas.

Mini–boss: un mutado gigante defendiendo el Núcleo.

💡 Mecánicas Extra (opcionales pero muy buenas para FXGL)
Mejoras

Entre niveles podés comprar:

más vida

más daño

cadencia

velocidad

granadas

FXGL ya tiene soporte para tiendas simples o pantallas aparte.

Loot dinámico

Munición

Botiquines

Buff temporales: daño x2, velocidad x2

Sistema de diálogos

Corto, con el comandante de la A.A.A. dando instrucciones.

🧱 Arquitectura sugerida para FXGL

Te dejo una estructura para que programes más cómodo:

Entidades

Player

Zombie

Spawner

Projectile

Pickup

LevelExit

Components

PlayerComponent

ZombieComponent

SpawnerComponent

HealthComponent

ShootingComponent

Game States

PlayState

PauseState

ShopState (opcional)

Levels

Mapas hechos en Tiled (.tmx) y cargados via FXGL.

📝 Resumen del Enfoque

Tu juego será:

Argentino

Narrativa simple pero sólida

Con 5 niveles diferenciados

Mecánicas fáciles de implementar

Enemies + spawners + objetivos claros

Realista, pero no demasiado complejo para un proyecto final
