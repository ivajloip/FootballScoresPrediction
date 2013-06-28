package bg.uni_sofia.fmi.football_predictor.engine;

import java.util.Random;

public class NeuralNetwork {

	public int numAttributes;
	public int numClasses;
	public int innerNodes;
	public double[][] inputToInnerEdges;
	public double[][] innerToOutputEdges;
	public Random random;
	public double speed = 0.1;

	public NeuralNetwork(int numAttributes, int numClasses, int innerNodes,
			double speed) {
		this.numAttributes = numAttributes;
		this.numClasses = numClasses;
		this.innerNodes = innerNodes;
		this.speed = speed;
		this.inputToInnerEdges = new double[numAttributes][innerNodes];
		this.innerToOutputEdges = new double[innerNodes][numClasses];
		this.random = new Random();
		this.init();
	}

	private void init() {

		for (int i = 0; i < inputToInnerEdges.length; i++)
			for (int j = 0; j < inputToInnerEdges[0].length; j++) {
				inputToInnerEdges[i][j] = -0.05 + (random.nextDouble() / 10);
			}

		for (int i = 0; i < innerToOutputEdges.length; i++)
			for (int j = 0; j < innerToOutputEdges[0].length; j++) {
				innerToOutputEdges[i][j] = -0.05 + (random.nextDouble() / 10);
			}
	}

	public int guess(double[] example) {
		double[] inner_layer = new double[this.innerNodes];
		double[] answer_layer = new double[this.numClasses];

		for (int i = 0; i < inner_layer.length; i++) {
			double sum = 0;
			for (int j = 0; j < this.numAttributes; j++)
				sum += example[j] * this.inputToInnerEdges[j][i];
			inner_layer[i] = 1 / (1 + Math.pow(Math.E, -sum));
		}

		for (int i = 0; i < answer_layer.length; i++) {
			double sum = 0;
			for (int j = 0; j < this.innerNodes; j++)
				sum += inner_layer[j] * this.innerToOutputEdges[j][i];
			answer_layer[i] = 1 / (1 + Math.pow(Math.E, -sum));
		}
		int max = 0;
		double maxProb = answer_layer[0];
		System.out.println("==============================================");
		for (int i = 0; i < answer_layer.length; i++) {
			System.out.println(answer_layer[i]);
			System.out.println();
			if (answer_layer[i] > maxProb) {
				maxProb = answer_layer[i];
				max = i;
			}
		}
		
		return max;
	}

	public void teach(double[] example, int kind) {

		double[] innerLayerValues = new double[this.innerNodes];
		double[] answerLayerValues = new double[this.numClasses];

		for (int i = 0; i < innerLayerValues.length; i++) {
			double sum = 0;
			for (int j = 0; j < this.numAttributes; j++)
				sum += example[j] * this.inputToInnerEdges[j][i];
			innerLayerValues[i] = 1 / (1 + Math.pow(Math.E, -sum));
		}

		for (int i = 0; i < answerLayerValues.length; i++) {
			double sum = 0;
			for (int j = 0; j < this.innerNodes; j++)
				sum += innerLayerValues[j] * this.innerToOutputEdges[j][i];
			answerLayerValues[i] = 1 / (1 + Math.pow(Math.E, -sum));
		}

		double[] expectedAnswer = new double[this.numClasses];
		for (int i = 0; i < this.numClasses; i++) {
			if (i == kind)
				expectedAnswer[i] = 1;
			else
				expectedAnswer[i] = 0;
		}

		double mistakeAnswer[] = new double[this.numClasses];

		for (int i = 0; i < this.numClasses; i++)
			mistakeAnswer[i] = answerLayerValues[i]
					* (1 - answerLayerValues[i])
					* (expectedAnswer[i] - answerLayerValues[i]);

		double mistakeInner[] = new double[this.innerNodes];

		for (int i = 0; i < this.innerNodes; i++) {
			double sum = 0;
			for (int j = 0; j < this.numClasses; j++)
				sum += mistakeAnswer[j] * this.innerToOutputEdges[i][j];
			mistakeInner[i] = innerLayerValues[i] * (1 - innerLayerValues[i])
					* sum;
		}

		for (int i = 0; i < this.numAttributes; i++)
			for (int j = 0; j < this.innerNodes; j++)
				inputToInnerEdges[i][j] += (this.speed * example[i] * mistakeInner[j]);

		for (int i = 0; i < this.innerNodes; i++)
			for (int j = 0; j < this.numClasses; j++)
				innerToOutputEdges[i][j] += (this.speed * innerLayerValues[i] * mistakeAnswer[j]);

	}

	public static void main(String[] args) {

		NeuralNetwork n = new NeuralNetwork(4, 2, 80, 0.1);

		n.init();
		double[] a = { 100, 200, 3, 4 };
		double[] a2 = { 105, 195, 7, 5 };
		double[] a3 = { 101, 210, 4, 6 };
		double[] a4 = { 104, 194, 6, 6 };
		for (int i = 0; i < 10; i++) {
			n.teach(a, 1);
			n.teach(a2, 1);
			n.teach(a3, 1);
		}
		n.guess(a4);

	}

}
