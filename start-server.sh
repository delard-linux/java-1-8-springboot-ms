#!/bin/bash

# ========================================
# Script para iniciar el servidor
# ========================================

echo "🚀 Iniciando servidor Spring Boot..."
echo ""

# Iniciar el servidor en segundo plano
./mvnw spring-boot:run > server.log 2>&1 &
SERVER_PID=$!

# Guardar el PID en un archivo
echo $SERVER_PID > .server.pid

echo "✓ Servidor iniciado con PID: $SERVER_PID"
echo "✓ Log: server.log"
echo ""
echo "Esperando a que el servidor esté listo..."

# Esperar hasta que el servidor responda
MAX_ATTEMPTS=30
ATTEMPT=0

while [ $ATTEMPT -lt $MAX_ATTEMPTS ]; do
    if curl -s http://localhost:8080/actuator/health > /dev/null 2>&1; then
        echo ""
        echo "================================================="
        echo "✓ Servidor iniciado correctamente"
        echo "✓ Puerto: 8080"
        echo "✓ PID: $SERVER_PID"
        echo "✓ Health: http://localhost:8080/actuator/health"
        echo "✓ API: http://localhost:8080/api/empresas"
        echo "✓ H2 Console: http://localhost:8080/h2-console"
        echo "================================================="
        exit 0
    fi
    
    echo -n "."
    sleep 1
    ATTEMPT=$((ATTEMPT + 1))
done

echo ""
echo "❌ ERROR: El servidor no respondió después de $MAX_ATTEMPTS segundos"
echo "   Revisa el log: tail -f server.log"
exit 1
