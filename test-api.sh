#!/bin/bash

# ========================================
# Script para probar la API con curl
# ========================================

BASE_URL="http://localhost:8080/api"
EMPRESA_ID=""
SEDE_ID=""

echo "================================================="
echo "🧪 PRUEBAS DE API - Gestión de Empresas"
echo "================================================="
echo ""

# Función para mostrar respuesta formateada
show_response() {
    echo "───────────────────────────────────────────────"
    echo "$1"
    echo "───────────────────────────────────────────────"
    echo "Respuesta:"
    echo "$2" | jq '.' 2>/dev/null || echo "$2"
    echo ""
}

# Verificar que el servidor esté funcionando
echo "1️⃣  Verificando que el servidor esté activo..."
HEALTH=$(curl -s http://localhost:8080/actuator/health)
if [ $? -ne 0 ]; then
    echo "❌ ERROR: El servidor no está activo"
    echo "   Ejecuta: ./start-server.sh"
    exit 1
fi
echo "✓ Servidor activo"
echo ""

# 2. Crear primera empresa
echo "2️⃣  Creando empresa: Tech Solutions..."
RESPONSE=$(curl -s -X POST "$BASE_URL/empresas" \
  -H "Content-Type: application/json" \
  -d '{
    "razonSocial": "Tech Solutions SL",
    "cif": "B12345678",
    "sector": "Tecnología",
    "telefono": "912345678",
    "email": "info@techsolutions.com",
    "numeroEmpleados": 50,
    "facturacionAnual": 2500000.00,
    "activo": true
  }')
show_response "POST /api/empresas" "$RESPONSE"
EMPRESA_ID=$(echo "$RESPONSE" | jq -r '.id' 2>/dev/null)

# 3. Crear segunda empresa
echo "3️⃣  Creando empresa: Green Energy..."
RESPONSE=$(curl -s -X POST "$BASE_URL/empresas" \
  -H "Content-Type: application/json" \
  -d '{
    "razonSocial": "Green Energy SA",
    "cif": "A87654321",
    "sector": "Energías Renovables",
    "telefono": "917654321",
    "email": "contact@greenenergy.com",
    "numeroEmpleados": 120,
    "facturacionAnual": 8500000.00,
    "activo": true
  }')
show_response "POST /api/empresas" "$RESPONSE"

# 4. Listar todas las empresas
echo "4️⃣  Listando todas las empresas..."
RESPONSE=$(curl -s "$BASE_URL/empresas")
show_response "GET /api/empresas" "$RESPONSE"

# 5. Obtener empresa por ID
if [ ! -z "$EMPRESA_ID" ] && [ "$EMPRESA_ID" != "null" ]; then
    echo "5️⃣  Obteniendo empresa por ID: $EMPRESA_ID..."
    RESPONSE=$(curl -s "$BASE_URL/empresas/$EMPRESA_ID")
    show_response "GET /api/empresas/$EMPRESA_ID" "$RESPONSE"
fi

# 6. Buscar por CIF
echo "6️⃣  Buscando empresa por CIF: B12345678..."
RESPONSE=$(curl -s "$BASE_URL/empresas/cif/B12345678")
show_response "GET /api/empresas/cif/B12345678" "$RESPONSE"

# 7. Buscar por sector
echo "7️⃣  Buscando empresas del sector Tecnología..."
RESPONSE=$(curl -s "$BASE_URL/empresas/sector/Tecnología")
show_response "GET /api/empresas/sector/Tecnología" "$RESPONSE"

# 8. Crear sede para la primera empresa
if [ ! -z "$EMPRESA_ID" ] && [ "$EMPRESA_ID" != "null" ]; then
    echo "8️⃣  Creando sede para empresa $EMPRESA_ID..."
    RESPONSE=$(curl -s -X POST "$BASE_URL/sedes" \
      -H "Content-Type: application/json" \
      -d "{
        \"nombre\": \"Sede Central Madrid\",
        \"direccion\": \"Calle Gran Vía 123\",
        \"ciudad\": \"Madrid\",
        \"codigoPostal\": \"28013\",
        \"pais\": \"España\",
        \"provincia\": \"Madrid\",
        \"telefono\": \"912345678\",
        \"email\": \"madrid@techsolutions.com\",
        \"esPrincipal\": true,
        \"empresaId\": $EMPRESA_ID
      }")
    show_response "POST /api/sedes" "$RESPONSE"
    SEDE_ID=$(echo "$RESPONSE" | jq -r '.id' 2>/dev/null)
fi

# 9. Listar todas las sedes
echo "9️⃣  Listando todas las sedes..."
RESPONSE=$(curl -s "$BASE_URL/sedes")
show_response "GET /api/sedes" "$RESPONSE"

# 10. Obtener sedes de la empresa
if [ ! -z "$EMPRESA_ID" ] && [ "$EMPRESA_ID" != "null" ]; then
    echo "🔟 Obteniendo sedes de la empresa $EMPRESA_ID..."
    RESPONSE=$(curl -s "$BASE_URL/sedes/empresa/$EMPRESA_ID")
    show_response "GET /api/sedes/empresa/$EMPRESA_ID" "$RESPONSE"
fi

# 11. Actualizar empresa
if [ ! -z "$EMPRESA_ID" ] && [ "$EMPRESA_ID" != "null" ]; then
    echo "1️⃣1️⃣  Actualizando empresa $EMPRESA_ID (incrementar empleados)..."
    RESPONSE=$(curl -s -X PUT "$BASE_URL/empresas/$EMPRESA_ID" \
      -H "Content-Type: application/json" \
      -d '{
        "razonSocial": "Tech Solutions SL",
        "cif": "B12345678",
        "sector": "Tecnología",
        "telefono": "912345678",
        "email": "info@techsolutions.com",
        "numeroEmpleados": 75,
        "facturacionAnual": 3000000.00,
        "activo": true
      }')
    show_response "PUT /api/empresas/$EMPRESA_ID" "$RESPONSE"
fi

# 12. Contar empresas activas
echo "1️⃣2️⃣  Contando empresas activas..."
RESPONSE=$(curl -s "$BASE_URL/empresas/activas/count")
show_response "GET /api/empresas/activas/count" "$RESPONSE"

# 13. Health check
echo "1️⃣3️⃣  Verificando health del servidor..."
RESPONSE=$(curl -s http://localhost:8080/actuator/health)
show_response "GET /actuator/health" "$RESPONSE"

# 14. Métricas
echo "1️⃣4️⃣  Obteniendo métricas del servidor..."
RESPONSE=$(curl -s http://localhost:8080/actuator/metrics)
show_response "GET /actuator/metrics" "$RESPONSE"

echo "================================================="
echo "✅ Todas las pruebas completadas"
echo "================================================="
echo ""
echo "📊 Resumen:"
echo "   • Empresas creadas: 2"
echo "   • Sedes creadas: 1"
echo "   • Total de peticiones: 14"
echo ""
