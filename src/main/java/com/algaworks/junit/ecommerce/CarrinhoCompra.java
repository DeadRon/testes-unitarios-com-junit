package com.algaworks.junit.ecommerce;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static java.math.BigDecimal.valueOf;

public class CarrinhoCompra {

	private final Cliente cliente;
	private final List<ItemCarrinhoCompra> itens;

	public CarrinhoCompra(Cliente cliente) {
		this(cliente, new ArrayList<>());
	}

	public CarrinhoCompra(Cliente cliente, List<ItemCarrinhoCompra> itens) {
		Objects.requireNonNull(cliente);
		Objects.requireNonNull(itens);
		this.cliente = cliente;
		this.itens = new ArrayList<>(itens); //Cria lista caso passem uma imutável
	}

	public List<ItemCarrinhoCompra> getItens() {
		//TODO deve retornar uma nova lista para que a antiga não seja alterada
		return new ArrayList<>(itens);
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void adicionarProduto(Produto produto, int quantidade) {

		//TODO parâmetros não podem ser nulos, deve retornar uma exception
		if (produto == null){
			throw new IllegalArgumentException();
		}
		//TODO quantidade não pode ser menor que 1
		if (quantidade < 1){
			throw new IllegalArgumentException();
		}

		Optional<ItemCarrinhoCompra> itemCarrinhoCompraOptional = itens.stream()
				.filter(icc -> icc.getProduto().equals(produto))
				.findFirst();

		//TODO deve incrementar a quantidade caso o produto já exista
		if (itemCarrinhoCompraOptional.isPresent()){
			int index = itens.indexOf(itemCarrinhoCompraOptional.get());
			itens.get(index).adicionarQuantidade(1);
		} else {
			ItemCarrinhoCompra itemCarrinhoCompra = new ItemCarrinhoCompra(produto, quantidade);
			itens.add(itemCarrinhoCompra);
		}
	}

	public void removerProduto(Produto produto) {
		//TODO parâmetro não pode ser nulo, deve retornar uma exception
		if (produto == null)
			throw new IllegalArgumentException();
		//TODO caso o produto não exista, deve retornar uma exception
		ItemCarrinhoCompra item = itens.stream()
				.filter(icc -> icc.getProduto().equals(produto))
				.findFirst()
				.orElseThrow(NoSuchElementException::new);
		//TODO deve remover o produto independente da quantidade
		itens.remove(item);
	}

	public void aumentarQuantidadeProduto(Produto produto) {
		//TODO parâmetro não pode ser nulo, deve retornar uma exception
		if (produto == null){
			throw new IllegalArgumentException("Não pode adicionar produto nulo");
		}
		//TODO caso o produto não exista, deve retornar uma exception
		Optional<ItemCarrinhoCompra> first = itens.stream().filter(itc -> itc.getProduto().equals(produto)).findFirst();
		if (first.isEmpty())
			throw new NoSuchElementException();
		else {
			//TODO deve aumentar em um quantidade do produto
			ItemCarrinhoCompra icc = first.get();
			itens.get(itens.indexOf(icc)).adicionarQuantidade(1);
		}

	}

    public void diminuirQuantidadeProduto(Produto produto) {
		//TODO parâmetro não pode ser nulo, deve retornar uma exception
		if (produto == null)
			throw new IllegalArgumentException();
		//TODO caso o produto não exista, deve retornar uma exception
		ItemCarrinhoCompra item = itens.stream()
				.filter(icc -> icc.getProduto().equals(produto))
				.findAny()
				.orElseThrow(NoSuchElementException::new);
		//TODO deve diminuir em um quantidade do produto, caso tenha apenas um produto, deve remover da lista
		item.subtrairQuantidade(1);
		if(item.getQuantidade() == 1){
			itens.remove(item);
		}
	}

    public BigDecimal getValorTotal() {
		//TODO implementar soma do valor total de todos itens
		return itens.stream()
				.map(icc -> valueOf(icc.getQuantidade()).multiply(icc.getProduto().getValor()))
				.reduce(BigDecimal.ZERO, BigDecimal::add);
    }

	public int getQuantidadeTotalDeProdutos() {
		//TODO retorna quantidade total de itens no carrinho
		//TODO Exemplo em um carrinho com 2 itens, com a quantidade 2 e 3 para cada item respectivamente, deve retornar 5
		return itens.stream()
				.mapToInt(ItemCarrinhoCompra::getQuantidade)
				.sum();
	}

	public void esvaziar() {
		//TODO deve remover todos os itens
		itens.removeAll(itens);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		CarrinhoCompra that = (CarrinhoCompra) o;
		return Objects.equals(itens, that.itens) && Objects.equals(cliente, that.cliente);
	}

	@Override
	public int hashCode() {
		return Objects.hash(itens, cliente);
	}
}