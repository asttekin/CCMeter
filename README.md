# CCMeter

##Screenshot
![](https://github.com/lhc-clover/CCMeter/blob/master/screenshots/sample.gif)

##Usage
Init
```Java
Meter meter = (Meter) findViewById(R.id.meter);
meter.setAngleRange(0, 360);
meter.setMaxValue(360);
meter.setMeterBackground(R.drawable.meter_bg);
meter.setMeterNeedle(R.drawable.meter_needle);
```
```XML
<cn.cctech.ccmeter.Meter
  android:id="@+id/meter"
  android:layout_width="wrap_content"
  android:layout_height="wrap_content"/>
```
Call `setValue`, meter will rotate to value with a accelerate-decelerate interpolator.
```Java
meter.setValue(value);
```
`setValue` when rotating is OK.


More detail in [sample code](https://github.com/lhc-clover/CCMeter/tree/master/Sample).
