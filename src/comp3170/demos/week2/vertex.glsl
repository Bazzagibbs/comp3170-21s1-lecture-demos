#version 410

in vec2 a_position;
in vec3 a_color;
out vec3 v_color;
uniform vec3 u_topColor;
uniform vec3 u_bottomColor;


void main(){
    gl_Position = vec4(a_position, 0, 1);
    // Use random vertex colour
    v_color = a_color;
/*
    // Use gradient;
    float p = (a_position.y + 1) / 2.0; // normalise to range [0, 1]
    v_color = mix(u_topColor, u_bottomColor, 1-p);
*/
}