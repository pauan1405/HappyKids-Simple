import React, { useState } from 'react';

const PREGUNTAS = [
  ['¿Hacen envíos a domicilio?',                   'Sí, hacemos envíos en toda la ciudad. Los costos y tiempos de entrega varían según la zona.'],
  ['¿Cuáles son los métodos de pago disponibles?', 'Aceptamos efectivo, tarjeta débito/crédito, transferencia bancaria y Nequi.'],
  ['¿Puedo hacer pedidos personalizados?',          'Por supuesto. Contáctanos con anticipación y diseñamos el paquete perfecto para tu evento.'],
  ['¿Tienen servicio de decoración para eventos?',  'Sí, ofrecemos paquetes completos de decoración para todo tipo de eventos.'],
  ['¿Cuál es el tiempo de entrega?',               'Para pedidos en la ciudad: 1-2 días hábiles. Pedidos especiales pueden tardar hasta 5 días.'],
];

export default function Faq() {
  const [abierto, setAbierto] = useState(null);
  return (
    <section className="faq-page">
      <div className="container">
        <h2>Preguntas Frecuentes</h2>
        {PREGUNTAS.map(([q, a], i) => (
          <div key={i} className={`faq-item ${abierto === i ? 'open' : ''}`} onClick={() => setAbierto(abierto === i ? null : i)}>
            <div className="faq-question">{q} <span className="faq-arrow">▼</span></div>
            <div className="faq-answer">{a}</div>
          </div>
        ))}
      </div>
    </section>
  );
}
