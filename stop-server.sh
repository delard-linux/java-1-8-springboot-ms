#!/bin/bash

# ========================================
# Script para detener el servidor
# ========================================

PID_FILE=".server.pid"

if [ ! -f "$PID_FILE" ]; then
    echo "❌ No se encontró el archivo PID: $PID_FILE"
    echo "   El servidor puede no estar ejecutándose o fue iniciado manualmente"
    echo ""
    echo "Buscando procesos de Spring Boot..."
    
    # Buscar procesos Java con spring-boot
    PIDS=$(ps aux | grep 'spring-boot:run' | grep -v grep | awk '{print $2}')
    
    if [ -z "$PIDS" ]; then
        echo "✓ No hay servidores Spring Boot ejecutándose"
        exit 0
    else
        echo "Procesos encontrados:"
        ps aux | grep 'spring-boot:run' | grep -v grep
        echo ""
        read -p "¿Detener estos procesos? (s/n): " -n 1 -r
        echo
        if [[ $REPLY =~ ^[Ss]$ ]]; then
            for PID in $PIDS; do
                echo "Deteniendo proceso $PID..."
                kill $PID
            done
            echo "✓ Procesos detenidos"
        fi
    fi
    exit 0
fi

# Leer el PID del archivo
SERVER_PID=$(cat "$PID_FILE")

echo "🛑 Deteniendo servidor con PID: $SERVER_PID"

# Verificar si el proceso existe
if ! ps -p $SERVER_PID > /dev/null 2>&1; then
    echo "⚠️  El proceso $SERVER_PID no está ejecutándose"
    rm -f "$PID_FILE"
    exit 0
fi

# Enviar señal SIGTERM
kill $SERVER_PID

# Esperar a que el proceso termine (máximo 10 segundos)
ATTEMPT=0
MAX_ATTEMPTS=10

echo -n "Esperando a que el servidor se detenga"
while [ $ATTEMPT -lt $MAX_ATTEMPTS ]; do
    if ! ps -p $SERVER_PID > /dev/null 2>&1; then
        echo ""
        echo "✓ Servidor detenido correctamente"
        rm -f "$PID_FILE"
        exit 0
    fi
    
    echo -n "."
    sleep 1
    ATTEMPT=$((ATTEMPT + 1))
done

echo ""
echo "⚠️  El servidor no se detuvo con SIGTERM, enviando SIGKILL..."
kill -9 $SERVER_PID

sleep 1

if ! ps -p $SERVER_PID > /dev/null 2>&1; then
    echo "✓ Servidor detenido forzosamente"
    rm -f "$PID_FILE"
    exit 0
else
    echo "❌ ERROR: No se pudo detener el servidor"
    exit 1
fi
