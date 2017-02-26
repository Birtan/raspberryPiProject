package com.example.service;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPin;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.wiringpi.GpioUtil;

@Component
public class LedService implements LedImpl {
	
	private static final String PIN_ACTIVATED = "pin activated";
	private static final String SHUTDOWN = "shutdown.";
	private static final String MY_LED = "MyLED";
	private static final String ON = "on";
	private static final String OFF = "off";
	private final Logger logger = Logger.getLogger(this.getClass());
	private static GpioPinDigitalOutput pin=null;
	private static final GpioController gpio = GpioFactory.getInstance();
	
	public static GpioController getGpio() {
		return gpio;
	}
	
	public Logger getLogger() {
		return logger;
	}
	
	/* (non-Javadoc)
	 * @see com.example.service.LedImpl#light()
	 */
	@Override
	public String light() {
		getInstanceAndPinHigh();
		pin.toggle();
		logger.info(ON);
		return ON;
	}
	
	/* (non-Javadoc)
	 * @see com.example.service.LedImpl#getInstanceAndPinHigh()
	 */
	@Override
	public String getInstanceAndPinHigh() {
		if (pin==null) {
			GpioUtil.enableNonPrivilegedAccess();
			 pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, MY_LED);
			 logger.info(PIN_ACTIVATED);
		}
		return PIN_ACTIVATED;
	}
	
	/* (non-Javadoc)
	 * @see com.example.service.LedImpl#on()
	 */
	@Override
	public String on() {
		getInstanceAndPinHigh();
		if (pin.isLow()) {
			pin.high();
			return ON;
		}else
		return OFF;
	}
	
	/* (non-Javadoc)
	 * @see com.example.service.LedImpl#off()
	 */
	@Override
	public String off() {
		getInstanceAndPinHigh();
		if (pin.isHigh()) {
			pin.low();
			return OFF;
		}else
		return ON;
	}
	
	/* (non-Javadoc)
	 * @see com.example.service.LedImpl#shutdown()
	 */
	@Override
	public String shutdown() {
		Collection<GpioPin> provisionedPins = gpio.getProvisionedPins();
		for (GpioPin gpioPin : provisionedPins) {
			gpioPin.getProvider().shutdown();
		}
		return SHUTDOWN;
	}


}
