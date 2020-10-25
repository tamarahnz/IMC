package dad.javafx.imc;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;
import javafx.scene.layout.HBox;

public class IndiceMasaCorporal extends Application {
	
	//Declaramos variables
	private DoubleProperty peso = new SimpleDoubleProperty(this,"peso");
	private StringProperty pesoString = new SimpleStringProperty(this,"pesoString");
	private DoubleProperty altura = new SimpleDoubleProperty(this,"altura");
	private StringProperty alturaString = new SimpleStringProperty(this,"alturaString");
	
	private DoubleProperty imc = new SimpleDoubleProperty(this,"imc");
	private StringProperty imcString = new SimpleStringProperty(this,"imcString");
	private StringProperty resultadoString = new SimpleStringProperty(this,"resultadoString");
	
	//Función que calcula el Índice Masa Corporal
	private void calculoIMC() {
		
		if (peso.get() == 0d || altura.get() == 0d) {
			imc.set(0d);
		}else {
			Double datosPeso = peso.get();
			Double datosAltura = altura.get();
			Double imcTotal;
			
			imcTotal = (datosPeso/((datosAltura*datosAltura)/100d)*100d);
			imc.set(Math.round(imcTotal *100d)/100d);
		}
		
	}
	
	//Función que muestra el resultado del IMC
	public void resultadoIMC() {
		Double imcTotal = imc.get();
		
		if (imcTotal == 0d) {
			imcString.set("IMC: (peso * altura^ 2");
			resultadoString.set("Bajo peso | Normal | Sobrepeso | Obeso");
		}else {
			imcString.set("IMC: " + imcTotal);
		}
			if (imcTotal < 18.5) {
				resultadoString.set("Bajo peso");
			}
			else if (imcTotal >= 18.5 && imcTotal < 25) {
				resultadoString.set("Normal");
			}
			else if (imcTotal >= 25 && imcTotal < 30) {
				resultadoString.set("Sobrepeso");
			}
			else {
				resultadoString.set("Obeso");
			}

	}

	public void start(Stage primaryStage) throws Exception {
		
		//Etiquetas
		Label imcLabel = new Label("IMC: (peso * altura^ 2)");
		Label resultadoLabel = new Label("Bajo peso | Normal | Sobrepeso | Obeso");
		
		//Bindings
		imcString.bindBidirectional(imcLabel.textProperty());
		resultadoString.bindBidirectional(resultadoLabel.textProperty());
		imc.addListener((o,ov,nv) -> resultadoIMC());
		
		TextField pesoText= new TextField();
		pesoString.bindBidirectional(pesoText.textProperty());
		Bindings.bindBidirectional(pesoString,peso,new NumberStringConverter());
		peso.addListener((o, ov, nv) -> calculoIMC());
		
		TextField alturaText = new TextField();
		alturaString.bindBidirectional(alturaText.textProperty());
		Bindings.bindBidirectional(alturaString,altura,new NumberStringConverter());
		altura.addListener((o,ov,nv) -> calculoIMC());
		
		//Creamos los HBox
		HBox pesoBox = new HBox();
		pesoBox.setAlignment(Pos.BASELINE_CENTER);
		pesoBox.setSpacing(5);
		pesoBox.getChildren().addAll(new Label("Peso:"),pesoText,new Label("kg"));
		
		HBox alturaBox = new HBox();
		alturaBox.setAlignment(Pos.BASELINE_CENTER);
		alturaBox.setSpacing(5);
		alturaBox.getChildren().addAll(new Label("Altura:"),alturaText,new Label("cm"));
		
		HBox totalBox = new HBox();
		totalBox.setAlignment(Pos.BASELINE_CENTER);
		totalBox.setSpacing(5);
		totalBox.getChildren().addAll(imcLabel);
		
		HBox resultadoBox = new HBox();
		resultadoBox.setAlignment(Pos.BASELINE_CENTER);
		resultadoBox.setSpacing(5);
		resultadoBox.getChildren().addAll(resultadoLabel);
		
		//Creamos el VBox
		VBox root = new VBox();
		root.setSpacing(5);
		root.setAlignment(Pos.CENTER);
		root.getChildren().addAll(pesoBox, alturaBox, totalBox, resultadoLabel);
		
		Scene escena = new Scene(root, 320, 200);
		
		primaryStage.setScene(escena);
		primaryStage.setTitle("Índice Masa Corporal");
		primaryStage.show();
		
	}

	public static void main(String[] args) {
		launch(args);
	}
	
}
