function initSpecialEffects(id){  
var canvas = document.getElementById(id);
        var cxt = canvas.getContext('2d');
       var w = canvas.width = window.innerWidth;
       var h = canvas.height = window.innerHeight;
       console.log(w)
       console.log(h)
        var time = 0;
        var EarchRotate = 0;
        var MoonX = 0;
        var MoonY = 0;
        // 行星轨道
        function starRoute(){
            for(var i=0; i<=8; i++){
                cxt.save();
                cxt.translate(w/2,h/2);
                cxt.strokeStyle = '#fff';
                cxt.beginPath();
                cxt.arc(0,0,i*50,0,2*Math.PI,false);
                cxt.closePath();
                cxt.stroke();
                cxt.restore();
            }
        }
        // 创建Start类型(其实在js中是对象)
        function Star(name,x,y,r,startColor,endColor,revolution){
            this.name = name;          // 星球名称
            this.x = x;                // 圆心 x,y
            this.y = y;
            this.r = r;                // 半径 r
            this.startColor = startColor;        // 渐变 start,end
            this.endColor = endColor;
            this.revolution = revolution;// 公转周期 revolution
        }
        Star.prototype = {
            constructor : Star,
            drawStar : function(){
                cxt.save();
                cxt.translate(w/2,h/2);
                // 绘制渐变颜色
                var gradient = cxt.createRadialGradient(this.x,this.y,0,this.x,this.y,this.r);
                gradient.addColorStop(0,this.startColor);
                gradient.addColorStop(1,this.endColor);
                cxt.fillStyle = gradient;
                cxt.beginPath();
                if(this.name == '地球'){
                    // 存储转动的角度,找到moon的旋转坐标
                    EarchRotate = time*360/this.revolution*Math.PI/180;
                    MoonX = 150*Math.sin(EarchRotate);
                    MoonY = -150*Math.cos(EarchRotate);
                    cxt.rotate(time*360/this.revolution*Math.PI/180);
                    cxt.arc(this.x,this.y,this.r,0,2*Math.PI,false);
                    cxt.fill();
                }else if(this.name == '月球'){
                    cxt.save();
                    cxt.translate(MoonX,MoonY); // 重置原点，即设置月球旋转中心
                    // 月球旋转轨迹
                    cxt.beginPath();
                    cxt.strokeStyle = 'rgba(207,207,207,0.2)';
                    cxt.arc(0,0,30,0,2*Math.PI,false);
                    cxt.stroke();
                    cxt.closePath();
                    // 月球圆心坐标
                    var x = 30*Math.cos((time*360/this.revolution-90)*Math.PI/180);
                    var y = 30*Math.sin((time*360/this.revolution-90)*Math.PI/180);
                    cxt.rotate(time*360/this.revolution*Math.PI/180);
                    // 月球的渐变颜色
                    var gradientM = cxt.createRadialGradient(x,y,0,x,y,this.r);
                    gradientM.addColorStop(0,this.startColor);
                    gradientM.addColorStop(1,this.endColor);
                    cxt.fillStyle = gradientM;
                    // 绘制月球
                    cxt.beginPath();
                    cxt.arc(x,y,this.r,0,2*Math.PI,false);
                    cxt.closePath();
                    cxt.fill();
                    cxt.restore();
                }else{
                    cxt.rotate(time*360/this.revolution*Math.PI/180);
                    cxt.arc(this.x,this.y,this.r,0,2*Math.PI,false);
                    cxt.fill();
                }
                cxt.closePath();
                cxt.restore();
                // 加上字体
                cxt.save();
                cxt.translate(w/2,h/2);
                cxt.fillStyle = '#fff';
                cxt.font = "bold " + 0.9*this.r + "px Arial";
                cxt.textAlign = "center";
                cxt.textBaseline = "middle";
                // cxt.rotate(time*360/this.revolution*Math.PI/180)
                if(this.name=='太阳'){
                    cxt.fillText(this.name,this.x,this.y);
                }else if(this.name=='月球'){
                    cxt.fillText('',this.x,this.y); // 月球太小，字体不显示
                }else{
                    cxt.fillText(this.name,this.x+50,this.y);
                }
                cxt.restore();
            }
        }
        function moveStar(){
            cxt.clearRect(0,0,w,h);   // 首先清除画布
            starRoute();
            var Sun = new Star('太阳',0,0,25,'#ffff00','#ff9900',0);
            var Mercury = new Star('水星',0,-50,8,'#a69697','#5c3e40',87.70);   // 水星
            var Venus = new Star('金星',0,-100,13,'#c4bbac','#1f1315',224.701);  // 金星
            var Earth = new Star('地球',0,-150,18,'#78b1e8','#050c12',365.2422); // 地球
            var Moon = new Star('月球',0,-180,6,'#a69697','#5c3e40',27.32); // 月球
            var Mars = new Star('火星',0,-200,10,'#cec9b6','#76422d',686.98); // 火星
            var Jupier = new Star('木星',0,-250,25,'#c0a48e','#322222',4332.589); // 木星
            var Saturn = new Star('土星',0,-300,23,'#f7f9e3','#5c4533',10759.5); // 土星
            var Uranus = new Star('天王星',0,-350,20,'#a7e1e5','#19243a',30799.095); // 天王星
            var Neptune = new Star('海王星',0,-400,20,'#0661b2','#1e3b73',60152); // 海王星
            Sun.drawStar();
            Mercury.drawStar();
            Venus.drawStar();
            Earth.drawStar();
            Moon.drawStar();
            Mars.drawStar();
            Jupier.drawStar();
            Saturn.drawStar();
            Uranus.drawStar();
            Neptune.drawStar();
            time++;
        }
    
        setInterval(moveStar,100)
}